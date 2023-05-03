package com.liuliang.demo3;

import java.time.LocalDate;
import java.util.function.Supplier;

/**
 * <p>Description: </p>
 *
 * @author <a href="mail to: lliang@outlook.com" rel="nofollow">liu liang</a>
 * @version v1.0, 2023/5/3 - 11:44
 */
public class LocalDateSupplier implements Supplier<LocalDate> {
    LocalDate start = LocalDate.of(2023, 5, 1);
    long n = -1;

    @Override
    public LocalDate get() {
        n++;
        return start.plusDays(n);
    }
}
