package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract void makeRemove(Object searchKey);

    protected abstract void replaceResume(Resume resume, Object searchKey);

    public abstract Resume getResume(Object searchKey);

    public abstract List<Resume> getAllFilledList();

    protected abstract void makeSave(Resume resume, Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean validate(Object searchKey);

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistedSearchKey(resume.getUuid());
        makeSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistedSearchKey(resume.getUuid());
        replaceResume(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistedSearchKey(uuid);
        makeRemove(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allFilledList = getAllFilledList();
        Collections.sort(allFilledList);
        return allFilledList;
    }

    private Object getExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!validate(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistedSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (validate(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
