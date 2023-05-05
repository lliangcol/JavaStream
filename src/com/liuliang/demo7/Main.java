package com.liuliang.demo7;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        // 流可以进行合并、去重、限制、跳过等操作
        // 合并（concat）
        // concat：合并两个流，返回一个由两个流的所有元素组成的流
        Stream<Integer> stream1 = Stream.of(1, 2, 3);
        Stream<Integer> stream2 = Stream.of(4, 5, 6);
        Stream<Integer> stream3 = Stream.concat(stream1, stream2);
        stream3.forEach(System.out::println);

        System.out.println("================================");
        // distinct：去重，通过流所生成元素的hashCode()和equals()去除重复元素
        Stream<Integer> stream4 = Stream.of(2, 4, 6, 8, 10);
        Stream<Integer> stream5 = Stream.concat(Stream.of(1, 2, 3, 4, 5, 6), stream4).distinct();
        stream5.forEach(System.out::println);

        // limit：限制从流中获得前n个数据
        List<Integer> integerList1 = Stream.iterate(1, i -> i + 2).limit(10).collect(Collectors.toList());
        System.out.println("integerList1 = " + integerList1);
        // skip：跳过前n个数据
        List<Integer> integerList2 = Stream.iterate(1, i -> i + 2).skip(3).limit(10).collect(Collectors.toList());
        System.out.println("integerList2 = " + integerList2);
    }

    public static void main7(String[] args) {
        // 排序（sorted）
        // sorted()：自然排序，流中元素需实现Comparable接口
        // sorted(Comparator com)：定制排序，自定义Comparator排序器

        List<Person> personList = initPersons();
        List<String> names1 = personList.stream().sorted(Comparator.comparing(Person::getSalary)).map(Person::getName).collect(Collectors.toList());
        System.out.println("names1 = " + names1);
        List<String> names2 = personList.stream().sorted(Comparator.comparing(Person::getSalary).reversed()).map(Person::getName).collect(Collectors.toList());
        System.out.println("names2 = " + names2);

        List<String> names3 = personList.stream().sorted(Comparator.comparing(Person::getSalary).thenComparing(Person::getAge)).map(Person::getName).collect(Collectors.toList());
        System.out.println("names3 = " + names3);
        List<String> names4 = personList.stream().sorted((p1, p2) -> {
            if (p1.getSalary() == p2.getSalary()) {
                return p2.getAge() - p1.getAge();
            } else {
                return p2.getSalary() - p1.getSalary();
            }
        }).map(Person::getName).collect(Collectors.toList());
        System.out.println("names4 = " + names4);
    }

    public static void main6(String[] args) {
        // 收集（collect）
        // collect：将流转换成其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
        // 因为流不存储数据，那么在流中的元素完成处理后，需要将流中的数据重新归集到新的集合里
        // Collector接口中方法的实现决定了如何对流执行收集操作（如收集到List、Set、Map）
        // Collectors实用类提供了很多静态方法，可以方便地创建常见收集器实例

        List<Integer> list1 = Arrays.asList(1, 6, 3, 4, 6, 7, 9, 6, 20);
        List<Integer> list2 = list1.stream().filter(i -> i % 2 == 0).collect(Collectors.toList());
        System.out.println("list2 = " + list2);
        Set<Integer> set1 = list1.stream().filter(i -> i % 2 == 0).collect(Collectors.toSet());
        System.out.println("set1 = " + set1);

        List<Person> persons = initPersons();
        Map<String, Person> map1 = persons.stream().filter(p -> p.getSalary() > 8000).collect(Collectors.toMap(Person::getName, p -> p));
        System.out.println("map1 = " + map1);

        // 统计（count、max、min、average、sum）
        // 求总数
        Long count = persons.stream().collect(Collectors.counting());
        System.out.println("count = " + count);

        // 求平均工资
        Double average = persons.stream().collect(Collectors.averagingDouble(Person::getSalary));
        System.out.println("average = " + average);

        // 求最高工资
        Optional<Integer> max = persons.stream().map(Person::getSalary).collect(Collectors.maxBy(Integer::compare));
        System.out.println("max = " + max.get());

        // 求工资之和
        Integer sum = persons.stream().collect(Collectors.summingInt(Person::getSalary));
        System.out.println("sum = " + sum);

        // 一次性统计所有信息
        DoubleSummaryStatistics collect = persons.stream().collect(Collectors.summarizingDouble(Person::getSalary));
        System.out.printf("%d %f %f %f \n", collect.getCount(), collect.getAverage(), collect.getMax(), collect.getSum());

        // 分组（groupingBy）分区（partitioningBy）
        // 分组是将流中的元素按照指定的分类进行收集，分区是分组的特殊情况，分区是将分组的key限制为true和false两种情况
        Map<Boolean, List<Person>> part = persons.stream().collect(Collectors.partitioningBy(p -> p.getSalary() > 8000));
        part.forEach((k, v) -> System.out.println(k + " " + v));

        Map<String, List<Person>> group = persons.stream().collect(Collectors.groupingBy(Person::getSex));
        group.forEach((k, v) -> System.out.println(k + " " + v));

        Map<String, Map<String, List<Person>>> group1 = persons.stream().collect(Collectors.groupingBy(Person::getSex, Collectors.groupingBy(Person::getArea)));
        group1.forEach((k, v) -> System.out.println(k + " " + v));

        // 接合（joining）
        // joining()方法可以将流中的元素用特定的连接符（没有的话，则直接连接）连接成一个字符串
        String names = persons.stream().map(Person::getName).collect(Collectors.joining(","));
        System.out.println("names = " + names);

        // 归约（reducing）
        // Collectors 类提供的 reducing 方法，相比于 stream 本身的 reduce 方法，增加了对自定义归约的支持
        // reducing()方法接收三个参数：
        // 1.初始值
        // 2.一个Function<T, U>来将元素类型T转换成U
        // 3.一个BinaryOperator<U>来将两个元素结合起来产生一个新值
        Integer sum1 = persons.stream().collect(Collectors.reducing(0, Person::getSalary, Integer::sum));
        System.out.println("sum1 = " + sum1);

        String names1 = persons.stream().collect(Collectors.reducing("", Person::getName, (s1, s2) -> s1 == "" ? s2 : s1 + "," + s2));
        System.out.println("names1 = " + names1);
    }

    public static void main5(String[] args) {
        // 归约（reduce）
        // 归约，也称缩减，是把一个流缩减成一个值，能实现对集合求和、求最值等操作
        // reduce()方法接收两个参数：
        // 1.初始值
        // 2.一个BinaryOperator<T>来将两个元素结合起来产生一个新值

        List<Integer> list = Arrays.asList(1, 3, 2, 8, 11, 4);
        Optional<Integer> sum1 = list.stream().reduce((x, y) -> x + y);
        Optional<Integer> sum2 = list.stream().reduce(Integer::sum);
        Integer sum3 = list.stream().reduce(0, Integer::sum);
        System.out.println("sum1 = " + sum1.get());
        System.out.println("sum2 = " + sum2.get());
        System.out.println("sum3 = " + sum3);

        Optional<Integer> max = list.stream().reduce((x, y) -> x > y ? x : y);
        Integer max1 = list.stream().reduce(1, Integer::max);
        System.out.println("max = " + max.get());
        System.out.println("max1 = " + max1);

        List<Person> persons = initPersons();
        Optional<Integer> sumSalary = persons.stream().map(Person::getSalary).reduce(Integer::sum);
        Integer sumSalary1 = persons.stream().reduce(0, (s, p) -> s += p.getSalary(), (s1, s2) -> s1 + s2);
        Integer sumSalary2 = persons.stream().reduce(0, (s, p) -> s += p.getSalary(), Integer::sum);
        System.out.println("sumSalary = " + sumSalary.get());
        System.out.println("sumSalary1 = " + sumSalary1);
        System.out.println("sumSalary2 = " + sumSalary2);

        Integer maxSalary = persons.stream().reduce(0, (m, p) -> m > p.getSalary() ? m : p.getSalary(), (m1, m2) -> m1 > m2 ? m1 : m2);
        Integer maxSalary1 = persons.stream().reduce(0, (m, p) -> m > p.getSalary() ? m : p.getSalary(), Integer::max);
        System.out.println("maxSalary = " + maxSalary);
        System.out.println("maxSalary1 = " + maxSalary1);
    }

    public static void main4(String[] args) {
        // 映射（map/flatMap）
        // map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素
        // flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
        String[] strArr = {"abcd", "bcdd", "defde", "fTr"};
        List<String> strList = Arrays.stream(strArr).map(String::toUpperCase).collect(Collectors.toList());
        strList.forEach(System.out::println);

        List<Integer> intList = Arrays.asList(1, 3, 5, 7, 9, 11);
        List<Integer> integerList = intList.stream().map(i -> i + 3).collect(Collectors.toList());
        integerList.forEach(System.out::println);

        List<Person> personList = initPersons();
        List<Person> personList1 = personList
                .stream()
                .map(p -> new Person(p.getName(), p.getAge(), p.getSalary() + 10000, p.getSex(), p.getArea()))
                .collect(Collectors.toList());
        System.out.println("first person = " + personList.get(0));
        System.out.println("first person = " + personList1.get(0));

        List<Person> personList2 = personList.stream().map(p -> {
            p.setSalary(p.getSalary() + 10000);
            return p;
        }).collect(Collectors.toList());

        System.out.println("first person = " + personList.get(0));
        System.out.println("first person = " + personList2.get(0));

        List<String> list = Arrays.asList("m, k, l, a", "1, 3, 5, 7");
        List<String> list1 = list.stream().flatMap(s -> {
            String[] split = s.split(",");
            return Arrays.stream(split);
        }).collect(Collectors.toList());

        System.out.println("list = " + list);
        System.out.println("list1 = " + list1);
    }

    public static void main3(String[] args) {
        // 聚合（max/min/count）
        List<String> list = Arrays.asList("java", "python", "c++", "php", "java");
        // 找出最长的单词
        Optional<String> max = list.stream().max(Comparator.comparing(String::length));
        System.out.println("max = " + max.get());

        List<Integer> list1 = Arrays.asList(7, 6, 9, 4, 11, 6);
        // 找出最大的元素
        Optional<Integer> max1 = list1.stream().max(Integer::compareTo);
        Optional<Integer> max2 = list1.stream().max(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2); // o1.compareTo(o2)
            }
        });
        System.out.println("max1 = " + max1.get());
        System.out.println("max2 = " + max2.get());

        long count = list1.stream().filter(i -> i > 6).count();
        System.out.println("count = " + count);

        List<Person> personList = initPersons();
        // 找出工资最高的人
        Optional<Person> max3 = personList.stream().max(Comparator.comparing(Person::getSalary));
        System.out.println("max3 = " + max3.get());
    }

    public static void main2(String[] args) {
        // 筛选（filter）
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);
        // 筛选出大于3的元素
        stream.filter(i -> i > 3).forEach(System.out::println);

        // 筛选出员工中工资高于8000的人的姓名，并形成新的集合
        List<Person> personList = initPersons();
        List<String> names = personList.stream().filter(p -> p.getSalary() > 8000).map(Person::getName).collect(Collectors.toList());
        System.out.println("names = " + names);
    }

    public static void main1(String[] args) {
        // 遍历/匹配（foreach/find/match）
        List<Integer> list = Arrays.asList(7, 6, 9, 3, 8, 2, 1);
        // 遍历输出符合条件的元素
        list.stream().filter(x -> x > 6).forEach(System.out::println);
        // 查找第一个符合条件的元素
        Optional<Integer> findFirst = list.stream().filter(x -> x > 6).findFirst();
        // 查找任意一个符合条件的元素
        Optional<Integer> findAny = list.parallelStream().filter(x -> x > 6).findAny();
        // 是否存在符合条件的元素
        boolean anyMatch = list.stream().anyMatch(x -> x < 6);
        System.out.println(findFirst.get());
        System.out.println(findAny.get());
        System.out.println(anyMatch);
    }

    static List<Person> initPersons() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person("Tom", 30, 8900, "male", "New York"));
        personList.add(new Person("Jack", 24, 7000, "male", "Washington"));
        personList.add(new Person("Lily", 26, 7800, "female", "Washington"));
        personList.add(new Person("Anni", 28, 8200, "female", "New York"));
        personList.add(new Person("Owen", 35, 9500, "male", "New York"));
        personList.add(new Person("Alisa", 26, 7900, "female", "New York"));

        return personList;
    }
}
