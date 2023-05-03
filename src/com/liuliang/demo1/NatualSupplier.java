package com.liuliang.demo1;

import java.util.function.Supplier;

/**
 * <p>Description: 生成自然数</p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 10:51
 */
public class NatualSupplier implements Supplier<Integer> {
    int n = 0;

    @Override
    public Integer get() {
        n++;
        return n;
    }
}
