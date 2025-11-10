package aud_2;

import java.util.function.*;

public class FunctionalInterfaces {
    public static void main(String[] args) {
        //1. Функционалност која прима еден стринг и враќа неговата должина
        Function<String, Integer> stringlength = str -> str.length();
        System.out.println("Length of 'Advanced Programing': " + stringlength.apply("Advanced Programing"));

        //2.Функционалност која прима два цели броја и враќа нивен збир
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;
        System.out.println("Sum of 5 and 3: " + sum.apply(5, 3));

        //3. Функционалност која прима еден цел број и враќа true ако е парен, инаку false
        Predicate<Integer> isEven = num -> num % 2 == 0;
        System.out.println("Is 4 even? : "+isEven.test(4));
        System.out.println("Is 3 even? : "+isEven.test(3));


        //4. Функционалност која прима еден стринг и го печати на конзола
        Consumer<String> printString= str -> System.out.println("Printing: "+str);
        printString.accept("Printing something..");

        //5. Функционалност која не прима аргументи и враќа тековно време во милисекунди
        Supplier<Long> currentTime= ()-> System.currentTimeMillis();
        System.out.println("Current time in milliseconds: "+currentTime.get());
    }
}
