package basejava.storage;

import basejava.Config;
import basejava.ResumeTestData;
import basejava.exception.ExistStorageException;
import basejava.exception.NotExistStorageException;
import basejava.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;
    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final String FULL_NAME_1 = "RockyBalboa";
    private static final String FULL_NAME_2 = "IvanDrago";
    private static final String FULL_NAME_3 = "ApolloCreed";
    private static final String FULL_NAME_4 = "PauliePennino";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.makeResume(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.makeResume(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.makeResume(UUID_3, FULL_NAME_3);
        RESUME_4 = ResumeTestData.makeResume(UUID_4, FULL_NAME_4);
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @After
    public void clearUp() {
        storage.clear();
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test
    public void update() {
        Resume resume = ResumeTestData.makeResume(UUID_2, "updatedName");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() {
        List<Resume> allSortedList = storage.getAllSorted();
        List<Resume> newList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(newList);
        assertEquals(3, allSortedList.size());
        assertEquals(newList, allSortedList);
    }

    private void assertGet(Resume r) {
        Resume n = storage.get(r.getUuid()); // резюме, загруженное из хранилища
        assertEquals(r, n);
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}