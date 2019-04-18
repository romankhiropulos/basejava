package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

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
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        int index = 0;
        for (Resume resume : list) {
            if (uuid.equals(resume.getUuid())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean validation(Object searchKey) {
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
