package storage;

import model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void makeSave(Resume resume, Resume searchKey) {
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
    public Resume makeGet(Resume searchKey) {
        return searchKey;
    }

    @Override
    public List<Resume> getAllFilledList() {
        return new ArrayList<>(map.values());
    }

    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isValidate(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected void makeRemove(Resume searchKey) {
        map.remove(searchKey.getUuid());
    }

    @Override
    protected void makeReplace(Resume resume, Resume searchKey) {
        map.replace(searchKey.getUuid(), resume);
    }
}
