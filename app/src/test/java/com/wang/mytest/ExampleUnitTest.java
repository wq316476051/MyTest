package com.wang.mytest;

import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.function.IntFunction;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        
        String[] array = {"adb", "dbd", "sadf", "cds"};
        Arrays.parallelSort(array, String::compareTo);
        System.out.println(Arrays.toString(array));

        Arrays.parallelPrefix(array, (s1, s2) -> {
            System.out.println("s1 = " + s1 + "; s2 = " + s2);
            return s2 + "1";
        });
        System.out.println(Arrays.toString(array));

        Arrays.parallelSetAll(array, index -> "item-" + index);
        System.out.println(Arrays.toString(array));
    }
}