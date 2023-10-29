package pro.sky.algorithms.service;

import pro.sky.algorithms.exceptions.ElementNotFoundException;

import java.util.Arrays;

public class StringListImpl implements StringList {
    private String[] arrayOfStrings = new String[0];
    private int countStrings = 0;

    public StringListImpl() {}

    public StringListImpl(String... strings) {
        this.arrayOfStrings = strings;
        countStrings = strings.length;
    }

    @Override
    public String add (String certainString) {
        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        if (arrayOfStrings.length == countStrings) {
            String[] newArrayOfStrings = new String[countStrings + 1];
            System.arraycopy(arrayOfStrings, 0, newArrayOfStrings, 0, arrayOfStrings.length);
            arrayOfStrings = newArrayOfStrings;
        }
        arrayOfStrings[countStrings] = certainString;
        countStrings++;

        return certainString;
    }

    @Override
    public String add (int index, String certainString) {
        if (index < 0 || index >= countStrings + 1) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }

        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        String[] newArrayOfStrings;
        if (arrayOfStrings.length == countStrings) {
            newArrayOfStrings = new String[arrayOfStrings.length + 1];
        } else {
            newArrayOfStrings = new String[arrayOfStrings.length];
        }

        System.arraycopy(arrayOfStrings, 0, newArrayOfStrings, 0, index);
        if (newArrayOfStrings.length - (index + 1) >= 0) {
            System.arraycopy(arrayOfStrings, index, newArrayOfStrings, index + 1, newArrayOfStrings.length - (index + 1));
        }

        arrayOfStrings = newArrayOfStrings;
        arrayOfStrings[index] = certainString;
        countStrings++;

        return certainString;
    }

    @Override
    public String set(int index, String certainString) {
        if (index >= countStrings) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }

        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        arrayOfStrings[index] = certainString;

        return certainString;
    }

    @Override
    public String remove(String certainString) {
        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        int index = 0;
        for (int i = 0; i < countStrings; i++) {
            if (arrayOfStrings[i].equals(certainString)) {
                arrayOfStrings[i] = null;
                index++;
            }
        }
        if (index == 0) {
            throw new ElementNotFoundException("Элемент массива не найден");
        }
        countStrings -= index;
        compress();

        return certainString;
    }

    @Override
    public String remove(int index) {
        if (index >= countStrings) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }
        String certainString = arrayOfStrings[index];
        arrayOfStrings[index] = null;
        countStrings--;
        compress();

        return certainString;
    }

    @Override
    public boolean contains(String certainString) {
        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        return indexOf(certainString) != -1;
    }

    @Override
    public int indexOf(String certainString) {
        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        for (int i = 0; i < countStrings; i++) {
            if (arrayOfStrings[i].equals(certainString)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(String certainString) {
        if (certainString == null) {
            throw new NullPointerException("Значение элемента массива не может быть пустым");
        }

        for (int i = countStrings - 1; i >= 0; i--) {
            if (arrayOfStrings[i].equals(certainString)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public String get(int index) {
        if (index >= countStrings) {
            throw new IndexOutOfBoundsException("Значение индекса не соответствует размеру массива");
        }
        return arrayOfStrings[index];
    }

    @Override
    public boolean equals(StringList otherList) {
        return Arrays.equals(toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return countStrings;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void clear() {
        arrayOfStrings = new String[0];
    }

    @Override
    public String[] toArray() {
        return Arrays.copyOf(arrayOfStrings, countStrings);
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray());
    }

    private void compress() {
        for (int i = 0; i < arrayOfStrings.length; i++) {
            if (arrayOfStrings[i] != null) {
                continue;
            }

            for (int j = i; j < arrayOfStrings.length; j++) {
                if (arrayOfStrings[j] != null) {
                    arrayOfStrings[i] = arrayOfStrings[j];
                    arrayOfStrings[j] = null;
                    break;
                }
            }
        }
    }
}
