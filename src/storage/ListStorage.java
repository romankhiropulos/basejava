package storage;

import model.Resume;

import java.util.*;

public class ListStorage extends ArrayStorage {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected void makeSave(Resume resume, Object searchKey) {
        list.add(resume);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume getResume(Object searchKey) {
        return list.get((int) searchKey);
    }

    @Override
    public List<Resume> getAllFilledList() {
        return list;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean validate(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void makeRemove(Object searchKey) {
        list.remove((int) searchKey);
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        list.set((int) searchKey, resume);
    }
}
