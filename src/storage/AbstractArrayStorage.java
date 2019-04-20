package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected static int resumeCounter = 0;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    protected abstract void removeResumeFromArray(int index);

    protected abstract void insertResume(Resume resume, int index);

    @Override
    protected boolean validation(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void makeSave(Resume resume, Object searchKey) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertResume(resume, (int) searchKey);
            resumeCounter++;
        }
    }

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected void makeRemove(Object searchKey) {
        removeResumeFromArray((int) searchKey);
        storage[resumeCounter - 1] = null;
        resumeCounter--;
    }

    @Override
    public Resume getResume(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, resumeCounter, null);
        resumeCounter = 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] resumes = Arrays.copyOf(storage, resumeCounter);
        Arrays.sort(resumes, RESUME_COMPARATOR);
        return Arrays.asList(resumes);
    }
}

