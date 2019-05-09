package basejava.storage;

import basejava.model.Resume;

import java.util.*;

public class MapUUIDStorage extends AbstractStorage<String> {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void makeSave(Resume resume, String searchKey) {
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
    public Resume makeGet(String searchKey) {
        return map.get(searchKey);
    }

    @Override
    public List<Resume> getAllFilledList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValidate(String searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    protected void makeRemove(String searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected void makeReplace(Resume resume, String searchKey) {
        map.replace(searchKey, resume);
    }
}
