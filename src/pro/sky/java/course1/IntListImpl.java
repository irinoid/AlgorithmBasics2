package pro.sky.java.course1;

import java.util.Arrays;

public class IntListImpl implements IntList {

    private Integer[] storage;
    private int size;

    public IntListImpl(int sizeStringArray) {
        if (sizeStringArray < 0) {
            throw new IllegalArgumentException("Недопустимый размер списка");
        }
        this.storage = new Integer[sizeStringArray];
    }

    private void validateIndex(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Индекс превышает длину списка");
        }
    }

    private Integer add(Integer item, int index) {
        validateIndex(index);
        storage[index] = item;
        size++;
        return storage[index];
    }

    @Override
    public Integer add(Integer item) {
        if (size == storage.length) {
            grow();
        }
        return add(item, size);
    }

    @Override
    public Integer add(int index, Integer item) {
        validateIndex(index);
        if (storage.length < size + 1) {
            grow();
        }
        System.arraycopy(storage, index, storage, index + 1, storage.length - index - 1);
        return add(item, index);
    }

    @Override
    public Integer set(int index, Integer item) {
        validateIndex(index);
        storage[index] = item;
        return storage[index];
    }

    @Override
    public Integer remove(Integer item) {
        int index = indexOf(item);
        Integer intTemp = storage[index];
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
            size--;
            if (checkFullness()){
                resize();
            }
            return intTemp;
        }
        throw new IllegalArgumentException("Элемента нет в списке");
    }

    private boolean checkFullness() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (!storage[i].equals(null)) {
                count++;
            }
        }
        if (count < storage.length / 2) {
            return true;
        }
        return false;
    }

    @Override
    public Integer remove(int index) {
        validateIndex(index);
        Integer intTemp = storage[index];
        System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
        size--;
        if (checkFullness()){
            resize();
        }
        return intTemp;
    }

    @Override
    public boolean contains(Integer item) {
        Integer[] storage2 = Arrays.copyOf(storage, size+1);
        mergeSort(storage2);
        return binarySearch(item, storage2);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = storage.length - 1; i >= 0; i--) {
            if (storage[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        validateIndex(index);
        return storage[index];
    }

    @Override
    public boolean equals(Integer[] otherList) {
        if (size != otherList.length) {
            return false;
        }
        return Arrays.equals(toArray(), otherList);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public Integer[] toArray() {
        return Arrays.copyOf(storage, size);
    }

    private void sortArray() {
        mergeSort(storage);
    }

    public static void mergeSort(Integer[] arr) {
        if (arr.length < 2) {
            return;
        }
        Integer mid = arr.length / 2;
        Integer[] left = new Integer[mid];
        Integer[] right = new Integer[arr.length - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = arr[mid + i];
        }

        mergeSort(left);
        mergeSort(right);

        merge(arr, left, right);
    }

    public static void merge(Integer[] arr, Integer[] left, Integer[] right) {

        Integer mainP = 0;
        Integer leftP = 0;
        Integer rightP = 0;
        while (leftP < left.length && rightP < right.length) {
            if (left[leftP] <= right[rightP]) {
                arr[mainP++] = left[leftP++];
            } else {
                arr[mainP++] = right[rightP++];
            }
        }
        while (leftP < left.length) {
            arr[mainP++] = left[leftP++];
        }
        while (rightP < right.length) {
            arr[mainP++] = right[rightP++];
        }
    }

    private boolean binarySearch(Integer item, Integer[] array) {
        int min = 0;
        int max = array.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == array[mid]) {
                return true;
            }

            if (item < array[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void grow() {
        Integer[] newIntArray = new Integer[storage.length + storage.length / 2];
        System.arraycopy(storage, 0, newIntArray, 0, storage.length);
        storage = newIntArray;
    }

    private void resize() {
        Integer[] newIntArray = new Integer[storage.length - storage.length / 3];
        System.arraycopy(storage, 0, newIntArray, 0, storage.length);
        storage = newIntArray;
    }

}
