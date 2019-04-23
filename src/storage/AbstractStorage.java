package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    protected abstract void makeSave(Resume resume, SK searchKey);

    public abstract Resume makeGet(SK searchKey);

    protected abstract void makeReplace(Resume resume, SK searchKey);

    protected abstract void makeRemove(SK searchKey);

    public abstract List<Resume> getAllFilledList();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isValidate(SK searchKey);

    @Override
    public void save(Resume resume) {
        SK searchKey = getNotExistedSearchKey(resume.getUuid());
        makeSave(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        return makeGet(searchKey);
    }

    @Override
    public void update(Resume resume) {
        SK searchKey = getExistedSearchKey(resume.getUuid());
        makeReplace(resume, searchKey);
    }

    @Override
    public void delete(String uuid) {
        SK searchKey = getExistedSearchKey(uuid);
        makeRemove(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> allFilledList = getAllFilledList();
        Collections.sort(allFilledList);
        return allFilledList;
    }

    private SK getExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isValidate(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistedSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isValidate(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
