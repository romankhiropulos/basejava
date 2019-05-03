package storage;

import model.Resume;

import java.util.Comparator;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "is absent");
        return binarySearch(storage, 0, resumeCounter, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertElement(Resume resume, int index) {
        int futureIndex = -index - 1 ;
        System.arraycopy(storage, futureIndex, storage, futureIndex + 1, resumeCounter - futureIndex);
        storage[futureIndex] = resume;
    }

    @Override
    protected void removeElementFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
    }
}
