package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        if(uuid != null) {
            for (int i = 0; i < resumeCounter; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    protected void insertResume(Resume resume) {
        storage[resumeCounter] = resume;
    }

    @Override
    protected void removeResume(String uuid) {
        int index = getIndex(uuid);
        storage[index] = storage[resumeCounter - 1];
        storage[resumeCounter - 1] = null;
    }
}