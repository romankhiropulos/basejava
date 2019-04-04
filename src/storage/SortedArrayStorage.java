package storage;

import model.Resume;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return binarySearch(storage, 0, resumeCounter, searchKey);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int futureIndex = -index - 1;
        System.arraycopy(storage, futureIndex, storage, futureIndex + 1, resumeCounter - futureIndex);
        storage[futureIndex] = resume;
    }

    @Override
    protected void removeResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
    }
}
