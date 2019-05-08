package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;

    private SerializationType serializationType;

    protected PathStorage(String dir, SerializationType serializationType) {
        Objects.requireNonNull(dir, "directory must not be null");
        directory = Paths.get(dir);
        this.serializationType = serializationType;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::makeRemove);
        } catch (IOException e) {
            throw new StorageException("Path delete error", e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory error", e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void makeReplace(Resume r, Path path) {
        try {
            serializationType.makeWrite(r, new BufferedOutputStream(new FileOutputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isValidate(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void makeSave(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path, path.getFileName().toString(), e);
        }
        makeReplace(r, path);
    }

    @Override
    public Resume makeGet(Path path) {
        try {
            return serializationType.makeRead(new BufferedInputStream(new FileInputStream(path.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.getFileName().toString(), e);
        }
    }

    @Override
    protected void makeRemove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.getFileName().toString(), e);
        }
    }

    @Override
    public List<Resume> getAllFilledList() {
        List<Resume> resumes = new ArrayList<>();
        try {
            Object[] objects = Files.list(directory).toArray();
            for (Object path : objects) {
                resumes.add(makeGet((Path) path));
            }
        } catch (IOException e) {
            throw new StorageException("Directory read error", e);
        }
        return resumes;
    }

}
