package com.liuliang.demo2;

import java.util.List;
import java.util.stream.Stream;

/**
 * <p>Description: 使用 map</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 11:25
 */
public class Main {
    public static void main(String[] args) {
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        Stream<Integer> stream2 = stream1.map(n -> n * n);
        stream2.forEach(System.out::println);

        List.of("  Apple ", " pear ", "ORANGE ", "bAnAnA")
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .forEach(System.out::println);
    }
}
