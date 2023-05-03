package com.liuliang.demo5;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Description: 输出集合</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 12:25
 */
public class Main {
    public static void main(String[] args) {
        Stream<Long> s1 = Stream.generate(new NatualSupplier());
        Stream<Long> s2 = s1.map(n -> n * n);
        Stream<Long> s3 = s2.map(n -> n - 1);
        System.out.println(s3);

        // 输出为List
        Stream<String> stream = Stream.of("Apple", "", null, "Pear", "   ", "Orange");
        List<String> list = stream.filter(s -> s != null && !s.trim().isEmpty()).collect(Collectors.toList());
        System.out.println(list);

        // 输出为数组
        List<String> list1 = List.of("Apple", "Pear", "Orange");
        String[] array = list1.stream().toArray(String[]::new);
        System.out.println(Arrays.toString(array));

        // 输出为 Map
        Stream<String> stream1 = Stream.of("APPL:Apple", "MSFT:Microsoft");
        Map<String, String> map = stream1.collect(Collectors
                .toMap(s -> s.substring(0, s.indexOf(":")), s -> s.substring((s.indexOf(":") + 1))));
        System.out.println(map);

        // 分组输出
        List<String> list2 = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots");
        Map<String, List<String>> groups = list2.stream().collect(Collectors.groupingBy(s -> s.substring(0, 1), Collectors.toList()));
        System.out.println(groups);
    }
}

class NatualSupplier implements Supplier<Long> {
    long n = 0;

    @Override
    public Long get() {
        n++;
        return n;
    }
}
