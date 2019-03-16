
/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private static int resumeCounter = 0;

    void clear() {
        for (int i = 0; i < resumeCounter; i++) {
            storage[i] = null;
        }
        resumeCounter = 0;
    }

    void save(Resume r) {
        if(resumeCounter < 10000) {
            for (int i = 0; i < 10000; i++) {
                if (storage[i] != null) {      // без этой проверки nullpointerexception
                    if (storage[i].uuid.equals(r.uuid)) {   // при добавлении первого резюме
                        System.out.println("Resume is exist already");
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
        else {
            System.out.println("Storage is full already");
        }
    }

    void update(Resume r){
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].equals(r)) {
                storage[i] = new Resume();
                storage[i].uuid = r.uuid;
                System.out.println("Successful update");
                break;
            }
            if(i == resumeCounter - 1){
                System.out.println("Resume didn't update");
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < resumeCounter; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
            if(i == resumeCounter - 1){
                System.out.println("Resume didn't find");
            }
        }
        return null;
    }

    void delete(String uuid) {
        int deletePosition;
        for (int i = 0; i < resumeCounter; i++) { // здесь при i = 0; i < resumeCounter
            if (storage[i].uuid.equals(uuid)) {   // и resumeCounter == 0 условие не выполняется
                deletePosition = i;               // и цикл вообще не начинается. В get так же
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
            if(i == resumeCounter - 1){
                System.out.println("Resume didn't find");
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