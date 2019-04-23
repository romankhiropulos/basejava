package storage;

import model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void makeSave(Resume resume, Object searchKey) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume getResume(Object searchKey) {
        String key = (String) searchKey;
        return map.get(key);
    }

    @Override
    public List<Resume> getAllFilledList() {
        return Arrays.asList(map.values().toArray(new Resume[0]));
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean validate(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected void makeRemove(Object searchKey) {
        String key = (String) searchKey;
        map.remove(key);
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        map.replace(resume.getUuid(), map.get(resume.getUuid()), resume);
    }
}
