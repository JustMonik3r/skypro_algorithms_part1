package pro.sky.algorithms;

import pro.sky.algorithms.service.IntegerListImpl;

public class Main {
    public static void main(String[] args) {

        Integer[] arr = generateRandomArray();
        long start = System.currentTimeMillis();
        IntegerListImpl.sortSelection(arr);
        System.out.println(System.currentTimeMillis() - start);
    }

    public static Integer[] generateRandomArray() {
        java.util.Random random = new java.util.Random();
        Integer[] arr = new Integer[100000];
        for (Integer i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100_000) + 100_000;
        }
        return arr;
    }
}