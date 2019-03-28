package storage;

import model.Resume;

import java.util.Arrays;

import static storage.MergeAndSearch.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume[] storageNew = getAll();
        MergeAndSearch.sortMergeStringNoRecursive(storageNew);
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        int result = -1;
        //if (binarySearch(uuid, storageNew) < 0) {
        if (Arrays.binarySearch(storageNew, 0, resumeCounter, searchKey) < 0) {
            return result;
        }
        else {
            for (int i = 0; i < resumeCounter; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    result = i;
                    break;
                }
            }
        }
        return result;
    }
}
