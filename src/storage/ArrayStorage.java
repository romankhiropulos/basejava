package storage;

import model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        if(uuid != null) {
            for (int i = 0; i < resumeCounter; i++) {
                if (uuid.equals(storage[i].getUuid())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        if(resume != null) {
            if (resume.getUuid() != null) {
                int index = getIndex(resume.getUuid());
                if (index == -1) {
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
                if (index != -1) {
                    System.out.println("Resume " + resume.getUuid() + " already exist");
                } else if (resumeCounter >= STORAGE_LIMIT) {
                    System.out.println("Storage overflow");
                } else {
                    storage[resumeCounter] = resume;
                    resumeCounter++;
                }
            }
        }
    }

    public void delete(String uuid) {
        if(uuid != null) {
            int index = getIndex(uuid);
            if (index == -1) {
                System.out.println("Resume " + uuid + " not exist");
            } else {
                storage[index] = storage[resumeCounter - 1];
                storage[resumeCounter - 1] = null;
                resumeCounter--;
            }
        }
    }
}