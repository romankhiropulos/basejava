package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends ArrayStorage {

    public static List<Resume> list = new ArrayList<>();

    @Override
    public void clearAllResumes() {
        list.clear();
    }

    @Override
    public Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    public Resume[] getFilledArray() {
        return list.toArray(new Resume[0]);
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
    protected void makeRemove(int index) {
        list.remove(index);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        list.remove(index);
        list.add(resume);
    }

}
