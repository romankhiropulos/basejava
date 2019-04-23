package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10_000;

    protected static int resumeCounter = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void removeElementFromArray(int index);

    protected abstract void insertElement(Resume resume, int index);

    @Override
    protected boolean isValidate(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void makeSave(Resume resume, Integer searchKey) {
        if (resumeCounter == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(resume, searchKey);
            resumeCounter++;
        }
    }

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    protected void makeReplace(Resume resume, Integer searchKey) {
        storage[searchKey] = resume;
    }

    @Override
    protected void makeRemove(Integer searchKey) {
        removeElementFromArray(searchKey);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

    @Override
    public Resume makeGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    @Override
    public List<Resume> getAllFilledList() {
        return Arrays.asList(Arrays.copyOf(storage, resumeCounter));
    }
}

