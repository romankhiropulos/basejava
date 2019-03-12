
/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private static int resumeCounter = 0;

    void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i] != null)
                storage[i] = null;
            else
                break;
        }
        resumeCounter = 0;
    }

    void save(Resume r) {
        for (int i = 0; i < 10000; i++) {
            if(storage[i] != null) {
                if (storage[i].uuid.equals(r.uuid))
                    break;
            }

            if (storage[i] == null) {
                resumeCounter++;
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume returnResume = null;
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                returnResume = storage[i];
                break;
            }
        }
        return returnResume;
    }

    void delete(String uuid) {
        boolean flag = false;
        int deletePosition = 0;
        for (int i = 0; i < resumeCounter; i++) {
            if(storage[i].uuid.equals(uuid)) {
                deletePosition = i;
                flag = true;
                break;
            }
        }
        if(flag) {
            for (int i = deletePosition; i < resumeCounter; i++) {
                if(i == resumeCounter - 1) {
                    storage[i] = null;
                    break;
                }
                storage[i] = storage[i + 1];
            }
            resumeCounter--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] storageNew = new Resume[resumeCounter];
        for (int i = 0; i < resumeCounter; i++)
            storageNew[i] = storage[i];
        return storageNew;
    }

    int size() {
        return resumeCounter;
    }
}