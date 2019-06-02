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
        int[] numbers = {5, 2, 1, 2, 8, 2};

        System.out.println(minValue(numbers));
        System.out.println(oddOrEven(Arrays.stream(numbers).boxed().collect(Collectors.toList())));
    }

    private static int minValue(int[] values) {
//        int result = integerSet.reduce(7, (amount, currentElement) -> {
//            counter += counter + 1;
//            amount += currentElement * Math. pow(10, integerSet.count() - counter);
//
//            return amount + currentElement;
//        });
        int result = 0;
        List<Integer> list = Arrays.stream(values).distinct().sorted().boxed().collect(Collectors.toList());
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i) * Math. pow(10, list.size() - 1 - i);
        }

        return result;
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int sum = integers.stream().reduce(0, (x, y) -> x + y);

        if(sum%2 == 0) {
            return integers.stream().filter(x -> x % 2 > 0).collect(Collectors.toList());
        } else if (sum%2 > 0) {
            return integers.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        }
        return integers;
    }
}
