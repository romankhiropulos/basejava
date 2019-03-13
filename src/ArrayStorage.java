
/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private static int resumeCounter = 0;

    void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            storage[i] = null;
        }
        resumeCounter = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < 10000; i++) {
            if(storage[i] != null) {
                if (storage[i].uuid.equals(r.uuid)) {
                    break;
                }
            }
            if (i == resumeCounter) {
                storage[resumeCounter] = r;
                resumeCounter++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if(storage[i] != null) {
                if (storage[i].uuid.equals(uuid)) {
                    return storage[i];
                }
            }
        }
        return null;
    }

    void delete(String uuid) {
        int deletePosition;
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i] != null) {
                if (storage[i].uuid.equals(uuid)) {
                    deletePosition = i;
                    for (int j = deletePosition; j < resumeCounter; j++) {
                        if (j == resumeCounter - 1) {
                            storage[j] = null;
                            break;
                        }
                        storage[j] = storage[j + 1];
                    }
                    resumeCounter--;
                    break;
                }
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageNew = new Resume[resumeCounter];
        for (int i = 0; i < resumeCounter; i++) {
            storageNew[i] = storage[i];
        }
        return storageNew;
    }

    int size() {
        return resumeCounter;
    }
}