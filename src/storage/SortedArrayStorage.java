package storage;

import model.Resume;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return binarySearch(storage, 0, resumeCounter, searchKey);
    }

    @Override
    protected void insertResume(Resume resume) {
        int futureIndex = Math.abs(getIndex(resume.getUuid())) - 1;
        System.arraycopy(storage, futureIndex, storage, futureIndex + 1, resumeCounter - futureIndex);
        storage[futureIndex] = resume;
    }

    @Override
    protected void removeResume(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
        storage[resumeCounter - 1] = null;
    }
}
