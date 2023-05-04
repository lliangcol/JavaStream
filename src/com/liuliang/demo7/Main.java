package com.liuliang.demo7;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
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
