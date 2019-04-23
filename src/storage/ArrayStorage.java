package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertElement(Resume resume, int searchKey) {
        storage[resumeCounter] = resume;
    }

    @Override
    protected void removeElementFromArray(int index) {
        storage[index] = storage[resumeCounter - 1];
    }
}