import model.Resume;
import storage.ListStorage;

public class MainListStorage {
    static final ListStorage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");
        Resume r3 = new Resume("uuid3");
        Resume r4 = new Resume("uuid4");


        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        ARRAY_STORAGE.update(r2);

        System.out.println("Get r3: " + ARRAY_STORAGE.getResumeFromList(r3.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.sizeCollection(ARRAY_STORAGE.getAllList()));

        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAllList();
        ARRAY_STORAGE.delete(r3.getUuid());
        printAllList();
        ARRAY_STORAGE.clearCollection(ARRAY_STORAGE.getAllList());
        printAllList();

        System.out.println("Size: " + ARRAY_STORAGE.sizeCollection(ARRAY_STORAGE.getAllList()));
    }

    static void printAllList() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllList()) {
            System.out.println(r);
        }

    }
}
