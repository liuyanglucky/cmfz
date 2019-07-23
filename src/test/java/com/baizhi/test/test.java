package com.baizhi.test;

public class test {
    public static void main(String[] args) {
        System.out.println(test1());
        System.out.println("================================");
        System.out.println(test2());
        System.out.println("================================");
        System.out.println(test3());
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("================================");
        System.out.println("================================");
    }

    public static Integer test1() {
        int i = 1;
        try {
            return i / 0;
        } catch (Exception e) {
            int c = 3;
            return c;
            //throw new RuntimeException("=========");
        } finally {
            int z = 2;
            System.out.println("1234");
            //return z;
        }

    }

    public static Integer test2() {
        int i = 1;
        try {
            return i;
        } catch (Exception e) {
            throw new RuntimeException("=========");
        } finally {
            ++i;
            System.out.println("12345");
            i++;
            System.out.println("finally语句是在try的return语句执行之后，return返回之前执行");
        }
    }

    public static Integer test3() {
        int b = 20;

        try {
            System.out.println("try block");
            b=b+80;
            return b;

        } catch (Exception e) {

            System.out.println("catch block");
            throw new RuntimeException("=========");
        } finally {

            System.out.println("finally block");

            if (b > 25) {
                System.out.println("b>25, b = " + b);
            }
        }

        //return b;
    }

}
