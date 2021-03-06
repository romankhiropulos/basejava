package basejava.main;

import basejava.model.Resume;
import basejava.storage.SortedArrayStorage;

/**
 * Test for your basejava.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();
    static SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    //static final ListStorage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", "Ivan");
        Resume r2 = new Resume("uuid2", "Roman");
        Resume r3 = new Resume("uuid3", "Natalie");
        Resume r4 = new Resume("uuid4", "Ivan");

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r4);

        ARRAY_STORAGE.update(r4);

        System.out.println("Get r4: " + ARRAY_STORAGE.get(r3.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(r3.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }

    }
}
