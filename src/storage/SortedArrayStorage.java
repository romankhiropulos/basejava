package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
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
        if (getIndex(resume.getUuid()) != -1) {
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
        sortMergeStringNoRecursive(storageNew);
        return storageNew;
    }

    protected int getIndex(String uuid) {
        return binarySearch(uuid, getAll());
    }

    private static void sortMergeStringNoRecursive(Resume[] arr) {
        int len = arr.length;
        int n = 1; // кратность сравнений (сравнивать по 1-му элем., 2-м ...)
        int shift; // сдвиг в перебираемом массиве
        int two_size; // количество элементов для 2-го массива
        Resume[] arr2;
        while (n < len) {
            shift = 0;
            while (shift < len) {
                if (shift + n >= len) {
                    break;
                }
                two_size = (shift + n * 2 > len) ? (len - (shift + n)) : n;
                arr2 = mergeResume(Arrays.copyOfRange(arr, shift, shift + n),
                        Arrays.copyOfRange(arr, shift + n, shift + n + two_size));
                for (int i = 0; i < n + two_size; ++i) {
                    arr[shift + i] = arr2[i]; // замена на отсортированное
                }
                shift += n * 2;
            }
            n *= 2;
        }
    }

    private static Resume[] mergeResume(Resume[] arr_1, Resume[] arr_2) {
        int len_1 = arr_1.length, len_2 = arr_2.length;
        int a = 0, b = 0, len = len_1 + len_2; // a, b - счетчики в массивах
        Resume[] result = new Resume[len];
        for (int i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (arr_1[a].getUuid().compareToIgnoreCase(arr_2[b].getUuid()) > 0) {
                    result[i] = arr_2[b++];
                }
                else {
                    result[i] = arr_1[a++];
                }
            } else if (b < len_2) {
                result[i] = arr_2[b++];
            } else {
                result[i] = arr_1[a++];
            }
        }
        return result;
    }

    public static int binarySearch (String key, Resume[] storage) {
        int lower = 0;
        int higher = storage.length - 1;
        while (lower <= higher) {
            int mid = lower + (higher - lower)/2;
            if (key.compareToIgnoreCase(storage[mid].getUuid()) < 0) {
                higher = mid - 1;
            }
            else if (key.compareToIgnoreCase(storage[mid].getUuid()) > 0) {
                lower = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
}
