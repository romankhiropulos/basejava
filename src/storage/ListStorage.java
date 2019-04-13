package storage;

import exception.NotExistStorageException;
import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends ArrayStorage {

    public static List<Resume> list = new ArrayList<>();

    public Resume getResumeFromList(String uuid) {
        Resume resume = new Resume(uuid);
        if(!list.contains(resume)) {
            throw new NotExistStorageException(uuid);
        }
        return resume;
    }

    public List<Resume> getAllList() {
        return list;
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return list.indexOf(searchKey );
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        list.add(resume);
    }

    @Override
    protected void removeResume(int index) {
        list.remove(index);
    }

    @Override
    protected void makeRemove(int index) {
        list.remove(index);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        list.remove(index);
        list.add(resume);
    }

}
