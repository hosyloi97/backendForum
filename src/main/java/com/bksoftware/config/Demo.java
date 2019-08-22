//package com.bksoftware.config;
//
//import java.util.Scanner;
//
//public class Demo {
//    public static void main(String[] args) {
//        int n;
//        Scanner sc = new Scanner(System.in);
//        System.out.println("enter value n:");
//        n = sc.nextInt();
//        int[] a= new int[n];
//        for (int i = 0; i < n; i++) {
//            System.out.println("enter value a[" + i + "]:");
//            a[i] = sc.nextInt();
//        }
//        printArray(a);
//        int [] b= mutateTheArray(n,a);
//        printArray(b);
//    }
//
//    public static int[] mutateTheArray(int n, int[] a) {
//        int[] b = new int[n];
//        for (int i = 0; i < n; i++) {
//            if ((i > 0) && (i < n - 1)) {
//                b[i] = a[i - 1] + a[i] + a[i + 1];
//            } else b[i] = 0;
//        }
//        return b;
//    }
//
//    public static void printArray(int[] a) {
//        for (int i = 0; i < a.length; i++) {
//            System.out.println("mutate[" + i + "]:" + a[i]);
//        }
//    }
//
//}
