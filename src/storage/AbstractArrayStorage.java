package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected static int resumeCounter = 0;

    protected static Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void removeResumeFromArray(int index);

    protected abstract void insertResume(Resume resume, int index);

    @Override
    protected void makeSave(Resume resume, int index) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, index);
        }
    }

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void makeRemove(int index) {
        removeResumeFromArray(index);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
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

