package basejava.storage.serializer;

import basejava.model.*;
import basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {

    @Override
    public void makeWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            writeCollection(dos, contacts.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sectionType = resume.getSections();
            writeCollection(dos, sectionType.entrySet(), entry -> {
                dos.writeUTF(entry.getKey().name());
                SectionType searchKey = entry.getKey();
                switch (searchKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ProgressSection progressSection = (ProgressSection) entry.getValue();
                        writeCollection(dos, progressSection.getProgress(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        LocationSection locationSection = (LocationSection) entry.getValue();
                        writeCollection(dos, locationSection.getLocations(), location -> {
                            dos.writeUTF(location.getLink().getLocationName());
                            dos.writeUTF(location.getLink().getLocationLink());
                            writeCollection(dos, location.getPositions(), position -> {
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            });
                        });
                        break;
                }
            });
        }
    }

    @Override
    public Resume makeRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
            readCollection(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                resume.addSection(sectionType, readSection(dis, sectionType));
            });
            return resume;
        }
    }

    @FunctionalInterface
    private interface Writable<T> {
        void writeItem(T t) throws IOException;
    }

    @FunctionalInterface
    private interface ElementProcessor {
        void process() throws IOException;
    }

    @FunctionalInterface
    private interface Readable<T> {
        T readItem() throws IOException;
    }

    private <T> void writeCollection(DataOutputStream dos, Collection<T> collection, Writable<T> writable) throws IOException {
        dos.writeInt(collection.size());
        for (T item : collection) {
            writable.writeItem(item);
        }
    }

    private void readCollection(DataInputStream dis, ElementProcessor processor) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            processor.process();
        }
    }

    private AbstractSection readSection(DataInputStream dis, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL:
            case OBJECTIVE:
                return new TextSection(dis.readUTF());
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return new ProgressSection(readList(dis, dis::readUTF));
            case EXPERIENCE:
            case EDUCATION:
                return new LocationSection(
                        readList(dis, () -> new Location(
                                new Link(dis.readUTF(), dis.readUTF()),
                                readList(dis, () -> new Location.Position(
                                        readDate(dis), readDate(dis), dis.readUTF(), dis.readUTF()
                                ))
                        )));
            default:
                throw new IllegalStateException();
        }
    }

    private <T> List<T> readList(DataInputStream dis, Readable<T> reader) throws IOException {
        int size = dis.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(reader.readItem());
        }
        return list;
    }

    private static void writeDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonth().getValue());
    }

    private static LocalDate readDate(DataInputStream dataInputStream) throws IOException {
        return DateUtil.of(dataInputStream.readInt(), Month.of(dataInputStream.readInt()));
    }
}
