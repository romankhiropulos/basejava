package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        storage[resumeCounter] = resume;
    }

    @Override
    protected void removeResume(int index) {
        storage[index] = storage[resumeCounter - 1];
        storage[resumeCounter - 1] = null;
    }
}