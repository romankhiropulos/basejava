package storage;

import model.Resume;

import java.util.Comparator;

import static java.util.Arrays.binarySearch;

public class SortedArrayStorage extends AbstractArrayStorage {

    /**
     * 1.
     * Для методов, как binarySearch, нужен массив, класс элементов которого имплементируют Comparable,
     * либо ему нужен какой-либо класс - сторонний компаратор, который позволяет сравнивать
     * два объекта. Можно создать один static final resumecomparator, чтобы только его прописать
     * в методе binarySearch, вместо того чтобы каждый раз создавать новый через new ResumeComparator.
     *
     * private static class ResumeComparator implements Comparator<Resume> {
     *         @Override
     *         public int compare(Resume o1, Resume o2) {
     *             return o1.getUuid().compareTo(o2.getUuid());
     *         }
     *     }
     * private static final ResumeComparator RESUME_COMPARATOR = new ResumeComparator();
     *
     * FOR EXAMPLE
     * 1) binarySearch(storage, 0, resumeCounter, searchKey, RESUME_COMPARATOR);
     * 2) Collections.sort(list, RESUME_COMPARATOR);
     *
     * 2.
     * Еще один способ это создание анонимного класса. Используем интерфейс Comparator.
     * Если в данном случае на RESUME_COMPARATOR вызвать метод getClass(),
     * то мы получим класс по имени имя$1 или имя$2 ... Этот класс создается на ходу в рантайме
     * и называется анонимным(не имеет имени).
     * Извне к нему обратиться нельзя(например, если в нем мы напишем поле int size, то работать
     * мы с этим полем сможем только внутри этого анонимного класса)
     *
     * private static final Comparator<Resume> RESUME_COMPARATOR = new Comparator<Resume>() {
     *         @Override
     *         public int compare(Resume o1, Resume o2) {
     *             return o1.getUuid().compareTo(o2.getUuid());
     *         }
     *     };
     *
     * 3.
     * Следующий еще более лаконичный вариант это использование лямбда выражения. Лямда выражения реализуются
     * не на основе создания анонимного класса, а на основе dynamic invoke(оптимизация на уровне jvm, была сделана,
     * чтобы скриптовым языкам проще работалось в джава машиной). Внутренняя реализация лямбы сделана эффективнее,
     * чем анонимный класс
     *
     * private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());
     *
     */
    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    /** Используем компаратор, так как compareTo класса Resume использует два параметра resume,
     * а у нас только uuid в getSearchKey
     */
    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "is absent");
        return binarySearch(storage, 0, resumeCounter, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertResume(Resume resume, int index) {
        int futureIndex = -index - 1;
        System.arraycopy(storage, futureIndex, storage, futureIndex + 1, resumeCounter - futureIndex);
        storage[futureIndex] = resume;
    }

    @Override
    protected void removeResumeFromArray(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCounter - index - 1);
    }
}
