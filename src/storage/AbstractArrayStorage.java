package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int resumeCounter = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);

    @Override
    public int size() {
        return resumeCounter;
    }

    @Override
    public Resume get(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                throw new NotExistStorageException(uuid);
            }
            return storage[index];
        }
        return null;
    }

    @Override
    public void save(Resume resume) {
        if(resume != null) {
            if(resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index >= 0) {
                    throw new ExistStorageException(resume.getUuid());
                } else if (resumeCounter == STORAGE_LIMIT) {
                    throw new StorageException("Storage overflow", resume.getUuid());
                } else {
                    insertResume(resume, index);
                    resumeCounter++;
                }
            }
        }
    }

    @Override
    public void update(Resume resume) {
        if(resume != null) {
            if (resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index < 0) {
                    throw new NotExistStorageException(resume.getUuid());
                } else {
                    storage[index] = resume;
                }
            }
        }
    }

    @Override
    public void delete(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                throw new NotExistStorageException(uuid);
            } else {
                removeResume(index);
                storage[resumeCounter - 1] = null;
                resumeCounter--;
            }
        }
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

