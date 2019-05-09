package basejava.storage;

import basejava.exception.StorageException;
import basejava.model.Resume;
import basejava.storage.serializer.SerializationStrategy;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;

    private SerializationStrategy serializationStrategy;

    protected FileStorage(File directory, SerializationStrategy serializationStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.directory = directory;
        this.serializationStrategy = serializationStrategy;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void makeSave(Resume resume, File newFile) {
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + newFile.getAbsolutePath(), newFile.getName(), e);
        }
        makeReplace(resume, newFile);
    }

    @Override
    public Resume makeGet(File file) {
        try {
            return serializationStrategy.makeRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void makeReplace(Resume resume, File newFile) {
        try {
            serializationStrategy.makeWrite(resume, new BufferedOutputStream(new FileOutputStream(newFile)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void makeRemove(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public List<Resume> getAllFilledList() {
        List<Resume> resumes = new ArrayList<>();
        for (File fileForList : Objects.requireNonNull(directory.listFiles(), "directory must not be null")) {
            resumes.add(makeGet(fileForList));
        }
        return resumes;
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
