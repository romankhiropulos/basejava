package storage;

import model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 7;
    protected static Resume[] storage = new Resume[STORAGE_LIMIT];
    protected static int resumeCounter = 0;

    public int size() {
        return resumeCounter;
    }

    public Resume get(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                System.out.println("Resume " + uuid + " not exist");
                return null;
            }
            System.out.println("successful action get");
            return storage[index];
        }
        return null;
    }

    protected abstract int getIndex(String uuid);

    public void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            storage[i] = null;
        }
        resumeCounter = 0;
    }

    public Resume[] getAll() {
        Resume[] storageNew = new Resume[resumeCounter];
        System.arraycopy(storage, 0, storageNew, 0, resumeCounter);
        return storageNew;
    }
}

