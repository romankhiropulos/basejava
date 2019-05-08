package storage;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationType {

    void makeWrite(Resume resume, OutputStream os) throws IOException;

    Resume makeRead(InputStream is) throws IOException;
}
