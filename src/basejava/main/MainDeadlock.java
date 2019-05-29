package basejava.main;


public class MainDeadlock {
    private static Thread THREAD_1;
    private static Thread THREAD_2;
    static class Printer{

        public <T> void print(T[] items){
            for(T item: items){
                System.out.println(item);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        //Arrays.asList( "a", "b", "d" ).forEach(System.out::println);

        Printer printer = new Printer();
        String[] people = {"Tom", "Alice", "Sam", "Kate", "Bob", "Helen"};
        Integer[] numbers = {23, 4, 5, 2, 13, 456, 4};

        THREAD_1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    THREAD_2.join();
                    printer.<String>print(people);
                    System.out.println("THREAD_1 finished");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        THREAD_2 = new Thread(() -> {
            try {
                THREAD_1.join();
                printer.<Integer>print(numbers);
                System.out.println("THREAD_2 finished");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        
        THREAD_1.start();
        //THREAD_1.join();  //deadlock
        THREAD_2.start();
    }
}
