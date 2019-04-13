package storage;

import exception.NotExistStorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {

    protected static Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    public int getSize() {
        return size();
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void makeRemove(int index) {
        removeResume(index);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

    @Override
    public Resume get(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                throw new NotExistStorageException(uuid);
            }
            return storage[index];
        }
        return null;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCounter);
    }
}

