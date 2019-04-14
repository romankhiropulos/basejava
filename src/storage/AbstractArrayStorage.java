package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {

    protected static Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected void replaceResume(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected void makeRemove(int index) {
        removeResumeFromArray(index);
        storage[resumeCounter - 1] = null;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public void clearAllResumes() {
        Arrays.fill(storage, 0, resumeCounter, null);
    }

    @Override
    public Resume[] getFilledArray() {
        return Arrays.copyOf(storage, resumeCounter);
    }
}

