package storage;

import exception.StorageException;
import model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{

    private File directory;

    protected abstract void makeWrite(Resume r, File file) throws IOException;

    protected abstract Resume makeRead(File file);

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void makeSave(Resume resume, File newFile) {
        try {
            newFile.createNewFile();
            makeWrite(resume, newFile);
        } catch (IOException e) {
            throw new StorageException("IO error", newFile.getName(), e);
        }
    }

    @Override
    public Resume makeGet(File file) {
        return makeRead(file);
    }

    @Override
    protected void makeReplace(Resume resume, File newFile) {
        try {
            makeWrite(resume, newFile);
        } catch (IOException e) {
            throw new StorageException("IO error", newFile.getName(), e);
        }
    }

    @Override
    protected void makeRemove(File file) {
        file.delete();
    }

    @Override
    public List<Resume> getAllFilledList() {
        List<Resume> fileArrayList = new ArrayList<>();
        for (File fileForList : Objects.requireNonNull(directory.listFiles(), "directory must not be null")) {
            fileArrayList.add(makeGet(fileForList));
        }
        return fileArrayList;
    }

    @Override
    protected boolean isValidate(File file) {
        return file.exists();
    }

    @Override
    public void clear() {
        for (File fileForDelete : Objects.requireNonNull(directory.listFiles(), "directory must not be null")) {
            makeRemove(fileForDelete);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list(), "directory must not be null").length;
    }
}
