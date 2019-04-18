package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void makeRemove(Object searchKey);

    protected abstract void replaceResume(Resume resume, Object searchKey);

    public abstract Resume getResume(Object searchKey);

    protected abstract void makeSave(Resume resume, Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean validation(Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (validation(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        makeSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!validation(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKey(resume.getUuid());
        if (!validation(searchKey)) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            replaceResume(resume, searchKey);
        }
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!validation(searchKey)) {
            throw new NotExistStorageException(uuid);
        } else {
            makeRemove(searchKey);
        }
    }
}
