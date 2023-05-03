package com.liuliang.demo4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * <p>Description: 使用 reduce</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 11:49
 */
public class Main {
    public static void main1(String[] args) {
        int[] nums = {1, 2, 3, 4};
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        System.out.println("sum = " + sum);

        // 使用 reduce
        int sum2 = 0;
        sum2 = Stream.of(1, 2, 3, 4).reduce(0, (acc, n) -> acc + n);
        System.out.println("sum2 = " + sum2);
    }

    public static void main(String[] args) {
        List<String> props = List.of("profile=native", "debug=true", "logging=warn", "interval=500");
        String debug = props.stream()
                .filter(kv -> kv.startsWith("debug"))
                .map(kv -> kv.split("\\=")[1])
                .findFirst()
                .orElse("");
        System.out.println("debug = " + debug);

        Map<String, String> map = props.stream()
                .map(kv -> {
                    String[] ss = kv.split("\\=", 2);
                    return Map.of(ss[0], ss[1]);
                })
                .reduce(new HashMap<>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        map.forEach((k, v) -> System.out.println(k + " = " + v));
    }
}
