package storage;

import model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int resumeCounter = 0;

    public int size() {
        return resumeCounter;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    public void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            storage[i] = null;
        }
        resumeCounter = 0;
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume " + resume.getUuid() + " not exist");
        } else {
            storage[index] = resume;
        }
    }

    public void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index != -1) {
            System.out.println("Resume " + resume.getUuid() + " already exist");
        } else if (resumeCounter >= STORAGE_LIMIT) {
            System.out.println("Storage overflow");
        } else {
            storage[resumeCounter] = resume;
            resumeCounter++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
        } else {
            storage[index] = storage[resumeCounter - 1];
            storage[resumeCounter - 1] = null;
            resumeCounter--;
        }
    }

    public Resume[] getAll() {
        Resume[] storageNew = new Resume[resumeCounter];
        System.arraycopy(storage, 0, storageNew, 0, resumeCounter);
        return storageNew;
    }
}

