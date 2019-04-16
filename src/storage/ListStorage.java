package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends ArrayStorage {

    private static List<Resume> list = new ArrayList<>();

    @Override
    protected void makeSave(Resume resume, int index) {
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
    public Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    protected int getIndex(String uuid) {
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
    protected void makeRemove(int index) {
        list.remove(index);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        list.set(index, resume);
    }

}
