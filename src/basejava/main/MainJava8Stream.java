package basejava.main;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
1) реализовать метод через стрим int minValue(int[] values).
Метод принимает массив цифр от 1 до 9, надо выбрать уникальные и вернуть минимально возможное число,
составленное из этих уникальных цифр. Не использовать преобразование в строку и обратно.
Например {1,2,3,3,2,3} вернет 123, а {9,8} вернет 89

2) реализовать метод List<Integer> oddOrEven(List<Integer> integers) если
сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные.
Сложность алгоритма должна быть O(N). Optional - решение в один стрим.
 */
public class MainJava8Stream {

    public static void main(String[] args) {
        int[] numbers = {8, 4, 2, 1, 1, 2, 5, 5};

        System.out.println(minValue(numbers));
        System.out.println(oddOrEven(Arrays.stream(numbers)
                .boxed()
                .collect(Collectors.toList())));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (x, y) -> 10 * x + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream()
                .reduce(0, Integer::sum);
        return integers.stream()
                .filter(x -> sum % 2 != x % 2)    // equals: "filter(x -> sum % 2 == 0 ? x % 2 > 0 : x % 2 == 0)"
                .collect(Collectors.toList());
    }
}
