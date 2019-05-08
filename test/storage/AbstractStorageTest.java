package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("/home/roman/basejava/storage");

    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "Ivan");
        RESUME_2 = new Resume(UUID_2, "Roman");
        RESUME_3 = new Resume(UUID_3, "Natalie");
        RESUME_4 = new Resume(UUID_4, "Ivan");

        RESUME_1.addContact(ContactType.MAIL, "mail1@ya.ru");
        RESUME_1.addContact(ContactType.PHONE, "11111");
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ProgressSection("Achivment11", "Achivment12", "Achivment13"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ProgressSection("Java", "SQL", "JavaScript"));
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new LocationSection(
                        new Location("Location11", "http://Location11.ru",
                                new Location.Position(2005, Month.JANUARY, "position1",
                                        "content1"),
                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
                                        "position2", "content2"))));
        RESUME_1.addSection(SectionType.EDUCATION,
                new LocationSection(
                        new Location("Institute", null,
                                new Location.Position(1996, Month.JANUARY, 2000, Month.DECEMBER,
                                        "aspirant", null),
                                new Location.Position(2001, Month.MARCH, 2005, Month.JANUARY,
                                        "student", "IT facultet")),
                        new Location("Location12", "http://Location12.ru")));
        RESUME_2.addContact(ContactType.SKYPE, "skype2");
        RESUME_2.addContact(ContactType.PHONE, "22222");
        RESUME_1.addSection(SectionType.EXPERIENCE,
                new LocationSection(
                        new Location("Location2", "http://Location2.ru",
                                new Location.Position(2015, Month.JANUARY, "position1",
                                        "content1"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_2);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2, "updatedName");
        storage.update(resume);
        Assert.assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertSize(2);
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() {
        List<Resume> allSortedList = storage.getAllSorted();
        List<Resume> newList = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        Collections.sort(newList);
        assertEquals(3, allSortedList.size());
        assertEquals(newList, allSortedList);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}