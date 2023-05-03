package com.liuliang.demo6;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>Description: 其它操作</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 16:48
 */
public class Main {
    public static void main(String[] args) {
        // 1. 排序
        List<String> list = List.of("Apple", "Banana", "Blackberry", "Coconut", "Avocado", "Cherry", "Apricots")
                .stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(list);

        List<String> list1 = List.of("Orange", "apple", "Banana")
                .stream()
                .sorted(String::compareToIgnoreCase)
                .collect(Collectors.toList());
        System.out.println(list1);

        // 2. 去重
        List<String> list2 = List.of("Apple", "Banana", "Apple", "Pear", "Orange")
                .stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println(list2);

        // 3. 截取
        List<Integer> list3 = List.of(1, 2, 3, 4, 5, 6)
                .stream()
                .skip(2)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(list3);

        // 4. 合并
        Stream<String> s1 = Stream.of("A", "B", "C");
        Stream<String> s2 = Stream.of("D", "E", "F");
        Stream<String> s3 = Stream.concat(s1, s2);
        System.out.println(s3.collect(Collectors.toList()));

        // 5. flatMap
        Stream<List<Integer>> s = Stream.of(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8, 9)
        );

        Stream<Integer> i = s.flatMap(list4 -> list4.stream());
        System.out.println(i.collect(Collectors.toList()));
    }
}
