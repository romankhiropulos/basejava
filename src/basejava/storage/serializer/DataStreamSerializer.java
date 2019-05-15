package basejava.storage.serializer;

import basejava.model.*;

import java.io.*;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static basejava.model.SectionType.*;

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
                if(entry.getKey().equals(PERSONAL) || entry.getKey().equals(OBJECTIVE)) {
                    TextSection textSection = (TextSection) entry.getValue();
                    dos.writeUTF(entry.getKey().name());
                    dos.writeUTF(textSection.getTextSection());
                } else if (entry.getKey().equals(QUALIFICATIONS) || entry.getKey().equals(ACHIEVEMENT)) {
                    ProgressSection progressSection = (ProgressSection) entry.getValue();
                    dos.writeUTF(entry.getKey().name());
                    dos.writeInt(progressSection.getProgress().size());
                    for (String x : progressSection.getProgress()) {
                        dos.writeUTF(x);
                    }
                } else if (entry.getKey().equals(EXPERIENCE) || entry.getKey().equals(EDUCATION)) {
                    LocationSection locationSection = (LocationSection) entry.getValue();
                    dos.writeUTF(entry.getKey().name());
                    dos.writeInt(locationSection.getLocation().size());
                    for (Location x : locationSection.getLocation()) {
                        dos.writeUTF(x.getLink().getLocation());
                        dos.writeUTF(x.getLink().getLocationLink());
                        dos.writeInt(x.getPositions().size());
                        for (Location.Position position : x.getPositions()) {
                            dos.writeInt(position.getStartDate().getYear());
                            dos.writeInt(position.getStartDate().getMonth().getValue());
                            dos.writeInt(position.getEndDate().getYear());
                            dos.writeInt(position.getEndDate().getMonth().getValue());
                            dos.writeUTF(position.getTitle());
                            dos.writeUTF(position.getDescription());
                        }
                    }
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
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                if(sectionType.equals(PERSONAL) || sectionType.equals(OBJECTIVE)) {
                    resume.addSection(sectionType, new TextSection(dis.readUTF()));
                } else if (sectionType.equals(QUALIFICATIONS) || sectionType.equals(ACHIEVEMENT)) {
                    List<String> progress = new ArrayList<>();
                    int sizeProgress = dis.readInt();
                    for (int j = 0; j < sizeProgress; j++) {
                        progress.add(dis.readUTF());
                    }
                    resume.addSection(sectionType, new ProgressSection(progress));
                } else if (sectionType.equals(EXPERIENCE) || sectionType.equals(EDUCATION)) {
                    List<Location> location = new ArrayList<>();
                    int sizeLocation = dis.readInt();
                    for (int j = 0; j < sizeLocation; j++) {
                        String linkName = dis.readUTF();
                        String link = dis.readUTF();
                        List<Location.Position> positions = new ArrayList<>();
                        int sizePositions = dis.readInt();
                        for (int k = 0; k < sizePositions; k++) {
                            int startYear = dis.readInt();
                            int startMonth = dis.readInt();
                            int endYear = dis.readInt();
                            int endMonth = dis.readInt();
                            String positionTitle = dis.readUTF();
                            String positionDescription = dis.readUTF();
                            positions.add(new Location.Position(startYear, Month.of(startMonth), endYear,
                                    Month.of(endMonth), positionTitle, positionDescription));
                        }
                        location.add(new Location(new Link(linkName, link), positions));
                    }
                    resume.addSection(sectionType, new LocationSection(location));
                }
            }
            return resume;
        }
    }
}
