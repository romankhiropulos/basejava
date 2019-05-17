package basejava.storage.serializer;

import basejava.model.*;
import basejava.util.DateUtil;

import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements SerializationStrategy {
    @Override
    public void makeWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> sectionType = resume.getSectionType();
            dos.writeInt(sectionType.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sectionType.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                SectionType searchKey = entry.getKey();
                switch (searchKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        TextSection textSection = (TextSection) entry.getValue();
                        dos.writeUTF(textSection.getTextSection());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ProgressSection progressSection = (ProgressSection) entry.getValue();
                        dos.writeInt(progressSection.getProgress().size());
                        for (String x : progressSection.getProgress()) {
                            dos.writeUTF(x);
                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        LocationSection locationSection = (LocationSection) entry.getValue();
                        dos.writeInt(locationSection.getLocation().size());
                        for (Location x : locationSection.getLocation()) {
                            dos.writeUTF(x.getLink().getLocation());
                            dos.writeUTF(x.getLink().getLocationLink());
                            dos.writeInt(x.getPositions().size());
                            for (Location.Position position : x.getPositions()) {
                                writeDate(dos, position.getStartDate());
                                writeDate(dos, position.getEndDate());
                                dos.writeUTF(position.getTitle());
                                dos.writeUTF(position.getDescription());
                            }
                        }
                        break;
                }
            }
        }
    }

    @Override
    public Resume makeRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int sizeContacts = dis.readInt();
            for (int i = 0; i < sizeContacts; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int sizeSections = dis.readInt();
            for (int i = 0; i < sizeSections; i++) {
                SectionType searchKey = SectionType.valueOf(dis.readUTF());
                switch (searchKey) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(searchKey, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> progress = new ArrayList<>();
                        int sizeProgress = dis.readInt();
                        for (int j = 0; j < sizeProgress; j++) {
                            progress.add(dis.readUTF());
                        }
                        resume.addSection(searchKey, new ProgressSection(progress));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Location> location = new ArrayList<>();
                        int sizeLocation = dis.readInt();
                        for (int j = 0; j < sizeLocation; j++) {
                            String linkName = dis.readUTF();
                            String link = dis.readUTF();
                            List<Location.Position> positions = new ArrayList<>();
                            int sizePositions = dis.readInt();
                            for (int k = 0; k < sizePositions; k++) {
                                LocalDate dateStart = readDate(dis.readInt(), Month.of(dis.readInt()));
                                LocalDate dateEnd = readDate(dis.readInt(), Month.of(dis.readInt()));
                                String positionTitle = dis.readUTF();
                                String positionDescription = dis.readUTF();
                                positions.add(new Location.Position(dateStart, dateEnd, positionTitle, positionDescription));
                            }
                            location.add(new Location(new Link(linkName, link), positions));
                        }
                        resume.addSection(searchKey, new LocationSection(location));
                        break;
                }
            }
            return resume;
        }
    }

    private static void writeDate(DataOutputStream dataOutputStream, LocalDate localDate) throws IOException {
        dataOutputStream.writeInt(localDate.getYear());
        dataOutputStream.writeInt(localDate.getMonth().getValue());
    }

    private static LocalDate readDate(int year, Month month) {
        return DateUtil.of(year, month);
    }
}
