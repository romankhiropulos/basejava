
import java.util.ArrayList;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    void clear() {
        storage = new Resume[10000];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null)
                storage[i] = null;
            else
                break;
        }
    }

    void save(Resume r) {
        ArrayList<Resume> arrayList = new ArrayList<>();
        int count = 0;
        while (storage[count] != null) {
            arrayList.add(storage[count]);
            count++;
        }

        boolean isExisted = false;
        for (Resume x : arrayList) {
            if(x.uuid.equals(r.uuid))
                isExisted = true;
        }
        if (!isExisted) {
            for (int i = 0; i < storage.length; i++) {
                if (storage[i] == null) {
                    storage[i] = r;
                    break;
                }
            }
        }
    }

    Resume get(String uuid) {
        Resume returnResume = null;
        for (int i = 0; i < storage.length; i++) {
            if(storage[i] != null) {
                if (storage[i].uuid.equals(uuid)) {
                    returnResume = storage[i];
                    break;
                }
            }
            else
                break;
        }
        return returnResume;
    }

    void delete(String uuid) {
        ArrayList <Resume> arrayList = new ArrayList<>();
        int count = 0;
        while (storage[count] != null) {
            arrayList.add(storage[count]);
            count++;
        }

        for (int i = 0; i < arrayList.size(); i++) {
            if(arrayList.get(i).uuid.equals(uuid))
                arrayList.remove(i);
        }

        storage = new Resume[10000];
        int i = 0;
        for (Resume x : arrayList) {
            storage[i] = x;
            i++;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int countOfResumes = 0;
        for (Resume x : storage) {
            if(x == null)
                break;
            countOfResumes++;
        }

        Resume[] storageNew = new Resume[countOfResumes];
        for (int i = 0; i < countOfResumes; i++)
            storageNew[i] = storage[i];

        return storageNew;
    }

    int size() {
        int i = 0;
        for (Resume x : storage) {
            if(x == null)
                break;
            i++;
        }
        return i;
    }
}
