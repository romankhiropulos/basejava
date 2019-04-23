package storage;

import model.Resume;

import java.util.*;

public class ListStorage extends AbstractStorage<Integer> {

    private List<Resume> list = new ArrayList<>();

    @Override
    protected void makeSave(Resume resume, Integer searchKey) {
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
    public Resume makeGet(Integer searchKey) {
        return list.get(searchKey);
    }

    @Override
    public List<Resume> getAllFilledList() {
        return new ArrayList<>(list);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (uuid.equals(list.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isValidate(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void makeRemove(Integer searchKey) {
        list.remove(searchKey.intValue());
    }

    @Override
    protected void makeReplace(Resume resume, Integer searchKey) {
        list.set(searchKey, resume);
    }
}
