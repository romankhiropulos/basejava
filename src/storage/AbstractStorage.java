package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.*;

public abstract class AbstractStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected static int resumeCounter = 0;

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume, int index);

    protected abstract void removeResume(int index);

    protected abstract void makeRemove(int index);

    protected abstract void replaceResume(Resume resume, int index);

    public abstract void clear();

    public abstract Resume get(String uuid);

    public abstract int getSize();

    public void clearCollection(Collection collection) {
        collection.clear();
    }

    public int sizeCollection(Collection collection) {
        return collection.size();
    }

    @Override
    public void save(Resume resume) {
        if(resume != null) {
            if(resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index >= 0) {
                    throw new ExistStorageException(resume.getUuid());
                } else if (getSize() == STORAGE_LIMIT) {
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
                    replaceResume(resume, index);
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
                makeRemove(index);
            }
        }
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}
