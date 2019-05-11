package basejava;

import basejava.model.*;

import java.time.Month;
import java.util.Map;

public class ResumeTestData {
    public static Resume RES_1;
    public static Resume RES_2;
    public static Resume RES_3;
    public static Resume RES_4;

    public static void main(String[] args) {
        print(ResumeTestData.RES_1.getContacts(), RES_1.getSectionType());
    }

    public static Resume[] makeResume(String uuid1, String fullName1, String uuid2, String fullName2, String uuid3,
                                       String uuid4) {

        RES_1 = new Resume(uuid1, fullName1);
        RES_1.addContact(ContactType.MAIL, "mail1@ya.ru");
        RES_1.addContact(ContactType.PHONE, "11111");
        RES_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RES_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RES_1.addSection(SectionType.ACHIEVEMENT, new ProgressSection("Achivment11", "Achivment12", "Achivment13"));
        RES_1.addSection(SectionType.QUALIFICATIONS, new ProgressSection("Java", "SQL", "JavaScript"));
        RES_1.addSection(SectionType.EXPERIENCE,
                new LocationSection(
                        new Location("Location11", "http://Location11.ru",
                                new Location.Position(2005, Month.JANUARY, "position1",
                                        "content1"),
                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
                                        "position2", "content2"))));
        RES_1.addSection(SectionType.EDUCATION,
                new LocationSection(
                        new Location("Institute", null,
                                new Location.Position(1996, Month.JANUARY, 2000, Month.DECEMBER,
                                        "aspirant", null),
                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
                                        "student", "IT facultet")),
                        new Location("Location12", "http://Location12.ru")));
        RES_1.addSection(SectionType.EXPERIENCE,
                new LocationSection(
                        new Location("Location2", "http://Location2.ru",
                                new Location.Position(2015, Month.JANUARY, "position1",
                                        "content1"))));

        RES_2 = new Resume(uuid2, fullName2);
        RES_2.addContact(ContactType.SKYPE, "skype2");
        RES_2.addContact(ContactType.PHONE, "22222");

        RES_3 = new Resume(uuid3);
        RES_4 = new Resume(uuid4);

        return new Resume[]{RES_1, RES_2, RES_3, RES_4};
    }

    private static void print(Map<ContactType, String> contacts, Map<SectionType, AbstractSection> sectionType) {
        for (Map.Entry<ContactType, String> pair : contacts.entrySet()) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }

        for (Map.Entry<SectionType, AbstractSection> pair : sectionType.entrySet()) {
            System.out.println(pair.getKey() + ": " + pair.getValue());
        }
    }
}
