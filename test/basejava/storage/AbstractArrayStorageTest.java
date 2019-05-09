package basejava.storage;

import basejava.exception.StorageException;
import basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume("fullName" + i));
            }
        }
        catch (StorageException e) {
            Assert.fail("failHappened");
        }
        storage.save(new Resume("fullName10000"));
    }
}