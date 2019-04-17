package storage;

import model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> map = new HashMap<>();

    @Override
    protected void makeSave(Resume resume, int index) {
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
    public Resume getResume(int index, String uuid) {
        return map.get(uuid);
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[map.size()];
        int resumesCounter = 0;
        for (Map.Entry<String, Resume> pair : map.entrySet()) {
            resumes[resumesCounter] = pair.getValue();
            resumesCounter++;
        }
        return resumes;
    }

    @Override
    protected int getIndex(String uuid) {
        if(map.get(uuid) != null) {
            return 1;
        }
//        for (Map.Entry<String, Resume> pair : map.entrySet()) {
//            if (uuid.equals(pair.getKey())) {
//                return 1;
//            }
//        }
        return -1;
    }

    @Override
    protected void makeRemove(int index, String uuid) {
        map.remove(uuid);
    }

    @Override
    protected void replaceResume(Resume resume, int index) {
        map.replace(resume.getUuid(), map.get(resume.getUuid()), resume);
    }
}
