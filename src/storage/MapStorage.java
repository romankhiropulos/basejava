package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

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
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        if(map.get(uuid) != null) {
            return uuid;
        }
        return null;
    }

    @Override
    protected boolean validation(Object searchKey) {
        return searchKey != null;
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
