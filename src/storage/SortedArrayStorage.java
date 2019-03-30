package storage;

import model.Resume;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return binarySearch(storage, 0, resumeCounter, searchKey);
    }

    public void update(Resume resume) {
        if(resume != null) {
            if (resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index < 0) {
                    System.out.println("Resume " + resume.getUuid() + " not exist");
                } else {
                    storage[index] = resume;
                }
            }
        }
    }

    public void save(Resume resume) {
        if(resume != null) {
            if(resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index >= 0) {
                    System.out.println("Resume " + resume.getUuid() + " already exist");
                } else if (resumeCounter == STORAGE_LIMIT) {
                    System.out.println("Storage overflow");
                } else {
                    storage[resumeCounter] = resume;
                    resumeCounter++;
                    for (int i = 0; i < resumeCounter; i++) {
                        if(resume.getUuid().compareTo(storage[i].getUuid()) < 0) {
                            System.arraycopy(storage, i, storage, i + 1, resumeCounter - i - 1);
                            storage[i] = resume;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void delete(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index < 0) {
                System.out.println("Resume " + uuid + " not exist");
            } else {
                System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
                storage[resumeCounter - 1] = null;
                resumeCounter--;
            }
        }
    }
}
