package com.liuliang.demo1;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * <p>Description: 创建 Stream</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 10:44
 */
public class Main {
    public static void main(String[] args) {
        // 1. Stream.of
        Stream<String> stream1 = Stream.of("a", "b", "c");
        stream1.forEach(System.out::println);

        // 2. 数组创建
        Stream<String> stream2 = Arrays.stream(new String[]{"A", "B", "C"});
        stream2.forEach(System.out::println);

        // 3. 集合创建
        Stream<String> stream3 = List.of("1", "2", "3").stream();
        stream3.forEach(System.out::println);

        // 4. Supplier
        Stream<Integer> natual = Stream.generate(new NatualSupplier());
        // 无限序列转换为有限序列再输出
        natual.limit(10).forEach(System.out::println);

        // 5. API
        try (Stream<String> lines = Files.lines(Paths.get("src/com/liuliang/demo1/Main.java"))) {
            lines.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("\\s+");
        Stream<String> stream4 = p.splitAsStream("The quick brown fox jumps over the lazy dog");
        stream4.forEach(System.out::println);

        // 6. 基本类型 Stream：IntStream、LongStream、DoubleStream
        IntStream intStream = Arrays.stream(new int[]{1, 2, 3});
        intStream.forEach(System.out::println);

        LongStream longStream = List.of(1L, 2L, 3L).stream().mapToLong(Long::longValue);
        longStream.forEach(System.out::println);

        LongStream fibonacci = Stream.generate(new FibonacciSupplier()).mapToLong(Long::longValue);
        fibonacci.limit(10).forEach(System.out::println);
    }
}

class FibonacciSupplier implements java.util.function.Supplier<Long> {
    long a = 0;
    long b = 1;

    @Override
    public Long get() {
        b += a;
        a = b - a;
        return a;
    }
}
