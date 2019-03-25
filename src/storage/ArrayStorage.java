package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final int STORAGE_LIMIT = 10_000;
    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private static int resumeCounter = 0;

    public void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            storage[i] = null;
        }
        resumeCounter = 0;
    }

    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) != -1) {
            System.out.println("model.Resume " + resume.getUuid() + " already exist");
        } else if (resumeCounter >= STORAGE_LIMIT) {
            System.out.println("Storage is full already");
        } else {
            storage[resumeCounter] = resume;
            resumeCounter++;
        }
    }

    public void update(Resume resume){
        if (getIndex(resume.getUuid()) == -1) {
            System.out.println("model.Resume " + resume.getUuid() + " is not exist");
        }
        else {
            storage[getIndex(resume.getUuid())] = resume;
            System.out.println("Update " + resume.getUuid() + " completed successfully");
        }
    }

    public Resume get(String uuid) {
        if (getIndex(uuid) == -1) {
            System.out.println("model.Resume " + uuid + " is not exist");
        }
        else {
            return storage[getIndex(uuid)];
        }
        return null;
    }

    public void delete(String uuid) {
        if (getIndex(uuid) != -1) {
            storage[getIndex(uuid)] = storage[resumeCounter - 1];
            storage[resumeCounter - 1] = null;
            resumeCounter--;
            System.out.println("model.Resume " + uuid + " deleted successfully");
        }
        else {
            System.out.println("model.Resume " + uuid + " is not exist");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] storageNew = new Resume[resumeCounter];
        System.arraycopy(storage, 0, storageNew, 0, resumeCounter);
        return storageNew;
    }

    public int size() {
        return resumeCounter;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}