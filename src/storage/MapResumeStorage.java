package storage;

import model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {

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
        return (Resume) searchKey;
    }

    @Override
    public List<Resume> getAllFilledList() {
        return Arrays.asList(map.values().toArray(new Resume[0]));
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean validate(Object searchKey) {
        return map.containsValue(searchKey);
    }

    @Override
    protected void makeRemove(Object searchKey) {
        Resume key = (Resume) searchKey;
        map.remove(key.getUuid());
    }

    @Override
    protected void replaceResume(Resume resume, Object searchKey) {
        Resume oldValue = (Resume) searchKey;
        map.replace(oldValue.getUuid(), oldValue, resume);
    }
}
