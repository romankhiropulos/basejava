package basejava;

import basejava.model.AbstractSection;
import basejava.model.ContactType;
import basejava.model.Resume;
import basejava.model.SectionType;

import java.util.Map;

public class ResumeTestData {
    
    public static void main(String[] args) {
        print(makeResume("uuid", "Bob"));
    }

    public static Resume makeResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        resume.addContact(ContactType.MAIL, "mail1@ya.ru");
        resume.addContact(ContactType.PHONE, "11111");
//        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
//        resume.addSection(SectionType.PERSONAL, new TextSection("Imba java developer"));
//        resume.addSection(SectionType.ACHIEVEMENT, new ProgressSection("Development of projects Practice Java",
//                "Implementation from scratch Rich Internet Application", "Shuttle launch"));
//        resume.addSection(SectionType.QUALIFICATIONS, new ProgressSection("Java", "SQL", "JavaScript"));
//        resume.addSection(SectionType.EXPERIENCE,
//                new LocationSection(
//                        new Location("Location2", "http://Location2.ru",
//                                new Location.Position(2015, Month.JANUARY, "position1",
//                                        "content1"))));
//        resume.addSection(SectionType.EDUCATION,
//                new LocationSection(
//                        new Location("Institute", null,
//                                new Location.Position(1996, Month.JANUARY, 2000, Month.DECEMBER,
//                                        "aspirant", null),
//                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
//                                        "student", "IT facultet")),
//                        new Location("Location12", "http://Location12.ru")));
//        resume.addSection(SectionType.EXPERIENCE,
//                new LocationSection(
//                        new Location("Location11", "http://Location11.ru",
//                                new Location.Position(2005, Month.JANUARY, "position1",
//                                        "content1"),
//                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
//                                        "position2", "content2"))));
        return resume;
    }

    private static void print(Resume resume) {
        for (Map.Entry<ContactType, String> pair : resume.getContacts().entrySet()) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> pair : resume.getSectionType().entrySet()) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }
}
