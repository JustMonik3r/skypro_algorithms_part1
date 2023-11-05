package pro.sky.algorithms.service;

import pro.sky.algorithms.exceptions.ElementNotFoundException;

import java.util.Arrays;

public class IntegerListImpl implements IntegerList {

    private static int lengthOfArray = 100000;
    public static Integer[] generateRandomArray() {
        java.util.Random random = new java.util.Random();
        Integer[] arr = new Integer[lengthOfArray];
        for (Integer i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100_000) + 100_000;
        }
        return arr;
    }
    private Integer[] arrayOfIntegers = generateRandomArray();
    private int countIntegers = 0;

    public IntegerListImpl() {}

    public IntegerListImpl(Integer... Integers) {
        this.arrayOfIntegers = Integers;
        countIntegers = Integers.length;
    }

    private void grow () {
        int newLength = lengthOfArray * 3/2 + 1;
        Integer[] newExpandedArrayOfIntegers = new Integer[newLength];
        System.arraycopy(arrayOfIntegers, 0, newExpandedArrayOfIntegers, 0, lengthOfArray);
        arrayOfIntegers = newExpandedArrayOfIntegers;
        lengthOfArray = newLength;
    }

    @Override
    public Integer add (Integer certainInteger) {
        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        if (arrayOfIntegers.length == countIntegers) {
            grow();
        }
        arrayOfIntegers[countIntegers] = certainInteger;
        countIntegers++;

        return certainInteger;
    }

    @Override
    public Integer add (int index, Integer certainInteger) {
        if (index < 0 || index >= countIntegers + 1) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }

        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        Integer[] newArrayOfIntegers = new Integer[0];
        if (arrayOfIntegers.length == countIntegers) {
            grow();
        } else {
            newArrayOfIntegers = new Integer[arrayOfIntegers.length];
        }

        System.arraycopy(arrayOfIntegers, 0, newArrayOfIntegers, 0, index);
        if (newArrayOfIntegers.length - (index + 1) >= 0) {
            System.arraycopy(arrayOfIntegers, index, newArrayOfIntegers, index + 1, newArrayOfIntegers.length - (index + 1));
        }

        arrayOfIntegers = newArrayOfIntegers;
        arrayOfIntegers[index] = certainInteger;
        countIntegers++;

        return certainInteger;
    }

    @Override
    public Integer set(int index, Integer certainInteger) {
        if (index >= countIntegers) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }

        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        arrayOfIntegers[index] = certainInteger;

        return certainInteger;
    }

    @Override
    public Integer remove(Integer certainInteger) {
        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        int index = 0;
        for (int i = 0; i < countIntegers; i++) {
            if (arrayOfIntegers[i].equals(certainInteger)) {
                arrayOfIntegers[i] = null;
                index++;
            }
        }
        if (index == 0) {
            throw new ElementNotFoundException("Элемент массива не найден");
        }
        countIntegers -= index;
        compress();

        return certainInteger;
    }

    @Override
    public Integer remove(int index) {
        if (index >= countIntegers) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }
        Integer certainInteger = arrayOfIntegers[index];
        arrayOfIntegers[index] = null;
        countIntegers--;
        compress();

        return certainInteger;
    }



    public boolean contains(Integer element) {
        Integer[] storageCopy = toArray();
        sortSelection(storageCopy);
        return binarySearch(storageCopy, element);
    }

    @Override
    public int indexOf(Integer certainInteger) {
        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        for (int i = 0; i < countIntegers; i++) {
            if (arrayOfIntegers[i].equals(certainInteger)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Integer certainInteger) {
        if (certainInteger == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        for (int i = countIntegers - 1; i >= 0; i--) {
            if (arrayOfIntegers[i].equals(certainInteger)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index >= countIntegers) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }
        return arrayOfIntegers[index];
    }

    @Override
    public boolean equals(IntegerList otherList) {
        return Arrays.equals(toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return countIntegers;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        arrayOfIntegers = new Integer[0];
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(arrayOfIntegers, countIntegers);
    }



    private void compress() {
        for (int i = 0; i < arrayOfIntegers.length; i++) {
            if (arrayOfIntegers[i] != null) {
                continue;
            }

            for (int j = i; j < arrayOfIntegers.length; j++) {
                if (arrayOfIntegers[j] != null) {
                    arrayOfIntegers[i] = arrayOfIntegers[j];
                    arrayOfIntegers[j] = null;
                    break;
                }
            }
        }
    }

    public static void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);

            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private static int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;

                swapElements(arr, i, j);
            }
        }

        swapElements(arr, i + 1, end);
        return i + 1;
    }


    public static void swapElements(Integer[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }


    public static void sortSelection(Integer[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minElementIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minElementIndex]) {
                    minElementIndex = j;
                }
            }
            swapElements(arr, i, minElementIndex);
        }
    }

    public boolean binarySearch (Integer[] arr, Integer element) {
        int min = 0;
        int max = arr.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (element == arr[mid]) {
                return true;
            }

            if (element < arr[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }
}
