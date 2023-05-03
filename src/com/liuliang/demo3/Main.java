package com.liuliang.demo3;

import java.time.DayOfWeek;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * <p>Description: 使用 filter</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 11:39
 */
public class Main {
    public static void main(String[] args) {
        IntStream.of(1, 2, 3, 4, 5, 6, 7, 8, 9)
                .filter(i -> i % 2 == 0)
                .forEach(System.out::println);

        Stream.generate(new LocalDateSupplier())
                .limit(31)
                .filter(ld -> ld.getDayOfWeek() == DayOfWeek.SATURDAY || ld.getDayOfWeek() == DayOfWeek.SUNDAY)
                .forEach(System.out::println);
    }
}
