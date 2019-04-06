package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume resume1 = new Resume("uuid1");
    private static final Resume resume2 = new Resume("uuid2");
    private static final Resume resume3 = new Resume("uuid3");
    private static final Resume resume4 = new Resume("uuid4");

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(resume1, storage.get("uuid1"));
        Assert.assertEquals(resume2, storage.get("uuid2"));
        Assert.assertEquals(resume3, storage.get("uuid3"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        Assert.assertEquals(resume, storage.get("uuid4"));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume("uuid2"));
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        for (int i = 3; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("uuid" + i));
        }
        storage.save(new Resume("uuid10000"));
    }

    @Test
    public void update() {
        Resume resume = new Resume("uuid2");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get("uuid2"));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete("uuid2");
        assertEquals(2, storage.size());
        storage.get("uui2");
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("UUID_4");
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] array = storage.getAll();
        Assert.assertEquals(array[0], storage.get("uuid1"));
        Assert.assertEquals(array[1], storage.get("uuid2"));
        Assert.assertEquals(array[2], storage.get("uuid3"));
        assertEquals(3, array.length);
    }
}