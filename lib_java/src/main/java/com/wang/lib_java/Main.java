package com.wang.lib_java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
//        streamRedirection();
//        floatError();

//        exchange();
//        encodeAndDecode();

//        leapYear();

//        printMultipyTable();

        System.out.println(0.3 / 0.1);

        Method[] declaredMethods = Main.class.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(method -> {
            Override override = method.getAnnotation(Override.class);
            if (override != null) {
                override.annotationType();
            }
        });

    }

    private static void printMultipyTable() {
        for (int column = 1; column <= 9; column++) {
            for (int row = 1; row <= column; row++) {
                System.out.print(row + " * " + column + " = " + column * row + "\t");
            }
            System.out.println();
        }
    }

    private static void leapYear() {
        System.out.println(isLeapYear(300));
        System.out.println(isLeapYear(2009));
        System.out.println(isLeapYear(2008));

//        ZoneId.getAvailableZoneIds().stream().forEach(s -> {
//            System.out.println("s = " + s);
//        });

        /*
        Asia/Shanghai
        Hongkong
         */
        LocalDate localDate = LocalDate.now();
        localDate = LocalDate.now(ZoneId.systemDefault());

        System.out.println(ZoneId.systemDefault());

        Date date = new Date();
        long time = date.getTime();
        System.out.println(time);

        LocalDate localDate1 = LocalDate.ofEpochDay(2008);
        System.out.println(localDate1);

        localDate = LocalDate.of(2008, 3, 1);
        boolean leapYear = localDate.isLeapYear();
        System.out.println(localDate);
        System.out.println(leapYear);
    }

    private static boolean isLeapYear(int year) {
        return (year % 4) == 0 && (year % 100) != 0 || (year % 400) == 0;
    }

    private static void encodeAndDecode() {
        int maxValue = Integer.MAX_VALUE;
        System.out.println(maxValue);

        int i = maxValue ^ 113;
        int i1 = i ^ 113;
        System.out.println(i1);

        String source = "Hello World";
        int key = 11342343;
        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        IntStream.range(0, bytes.length).forEach(index -> {
            bytes[index] ^= key;
        });

        String encoded = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("encoded = " + encoded);

        byte[] encodedBytes = encoded.getBytes(StandardCharsets.UTF_8);
        IntStream.range(0, encodedBytes.length).forEach(index -> {
            encodedBytes[index] ^= key;
        });

        String decoded = new String(encodedBytes, StandardCharsets.UTF_8);
        System.out.println("decoded = " + decoded);
    }

    private static void exchange() {
        int a = 3;
        int b = 5;

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;
        System.out.println(a);
        System.out.println(b);
    }

    private static void streamRedirection() {
        try (FileWriter writer = new FileWriter("./lib_java/test.txt")) {
            String encoding = writer.getEncoding();
            System.out.println("encoding = " + encoding);

            writer.write("Hello World");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("main");
        try {
            System.setIn(new FileInputStream("./lib_java/test.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println("next = " + s);

        scanner.close();
    }

    private static void floatError() {
        System.out.println(0.1 + 0.2);
        System.out.println(0.2 - 0.1);
        System.out.println(0.1 * 0.2);
        System.out.println(0.2 / 0.1);

        System.out.println(0.3 - 0.1);
        System.out.println(0.3 / 0.1);
        /*
        0.30000000000000004
        0.1
        0.020000000000000004
        2.0
        0.19999999999999998
        2.9999999999999996
         */

        System.out.println(BigDecimal.valueOf(0.1).add(BigDecimal.valueOf(0.2)));
        System.out.println(BigDecimal.valueOf(0.2).subtract(BigDecimal.valueOf(0.1)));
        System.out.println(BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(0.2)));
        System.out.println(BigDecimal.valueOf(0.2).divide(BigDecimal.valueOf(0.1)));

        System.out.println(BigDecimal.valueOf(0.3).subtract(BigDecimal.valueOf(0.1)));
        System.out.println(BigDecimal.valueOf(0.3).divide(BigDecimal.valueOf(0.1)));
        /*
        0.3
        0.1
        0.02
        2
        0.2
        3
         */

         System.out.println(BigDecimal.valueOf(0.2).divide(BigDecimal.valueOf(0.3))); // 抛异常
        /*
        Exception in thread "main" java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result.
         */
    }
}
