package pro.sky.java.course1;

import java.util.Arrays;

public class IntListImpl implements IntList {

    private Integer[] intArray;

    public IntListImpl(int sizeStringArray) {
        this.intArray = new Integer[sizeStringArray];
    }

    @Override
    public Integer add(Integer item) {

        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i].equals(null)) {
                intArray[i] = item;
                return intArray[i];
            }
        }
        int addItemIndex = intArray.length;
        grow();
        intArray[addItemIndex] = item;
        return intArray[addItemIndex];
    }

    @Override
    public Integer add(int index, Integer item) {
        for (int i = 0; i < index; i++) {
            if (intArray[i].equals(null)) {
                throw new IndexOutOfBoundsException("Индекс превышает длину списка");
            }
        }
        if (index < intArray.length) {
            grow();
            System.arraycopy(intArray, index, intArray, index + 1, intArray.length - index - 1);
            intArray[index] = item;
            return intArray[index];
        }
        throw new IndexOutOfBoundsException("Индекс превышает длину массива");
    }

    @Override
    public Integer set(int index, Integer item) {
        for (int i = 0; i < index; i++) {
            if (intArray[i].equals(null)) {
                throw new IndexOutOfBoundsException("Индекс превышает длину списка");
            }
        }
        if (index < intArray.length - 1) {
            intArray[index] = item;
            return intArray[index];
        }
        throw new IndexOutOfBoundsException("Индекс превышает длину массива");
    }

    @Override
    public Integer remove(Integer item) {
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i].equals(item)) {
                Integer intTemp = intArray[i];
                for (int j = i + 1; j < intArray.length; j++) {
                    intArray[j] = intArray[j - 1];
                }
                Integer[] intArrayTemp = new Integer[intArray.length - 1];
                System.arraycopy(intArray, 0, intArrayTemp, 0, intArray.length - 1);
                intArray = intArrayTemp;
                if (checkFullness()){
                    resize();
                }
                return intTemp;
            }
        }
        throw new IllegalArgumentException("Элемента нет в списке");
    }

    private boolean checkFullness(){
        int count=0;
        for (int i =0; i< intArray.length; i++){
            if (!intArray[i].equals(null)){
                count ++;
            }
        }
        if (count<intArray.length/2){
            return true;
        }
        return false;
    }

    @Override
    public Integer remove(int index) {
        if (index < intArray.length) {
            Integer intTemp = intArray[index];
            for (int i = index; i < intArray.length; i++) {
                intArray[i] = intArray[i + 1];
            }
            Integer[] intArrayTemp = new Integer[intArray.length - 1];
            System.arraycopy(intArray, 0, intArrayTemp, 0, intArray.length - 1);
            intArray = intArrayTemp;
            return intTemp;
        }
        throw new IndexOutOfBoundsException("Индекс превышает длину массива");
    }

    @Override
    public boolean contains(Integer item) {
        sortArray();
        return binarySearch(item);
    }

    @Override
    public int indexOf(Integer item) {
        for (int i = 0; i < intArray.length; i++) {
            if (intArray[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Integer item) {
        for (int i = intArray.length - 1; i >= 0; i--) {
            if (intArray[i].equals(item)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public Integer get(int index) {
        if (index < intArray.length) {
            return intArray[index];
        }
        throw new IndexOutOfBoundsException("Индекс превышает размер массива");
    }

    @Override
    public boolean equals(Integer[] otherList) {
        if (intArray.length != otherList.length) {
            return false;
        } else {
            for (int i = 0; i < intArray.length; i++) {
                if (!intArray[i].equals(otherList[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int size() {
        int listSize = 0;
        for (int i = 0; i < intArray.length; i++) {
            if (!intArray[i].equals(null)) {
                listSize++;
            }
        }
        return listSize;
    }

    @Override
    public boolean isEmpty() {
        for (int i = 0; i < intArray.length; i++) {
            if (!intArray[i].equals(null)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        Arrays.fill(intArray, null);
    }

    @Override
    public Integer[] toArray() {
        Integer[] newIntArray = new Integer[intArray.length];
        int j = 0;
        for (int i = 0; i < intArray.length; i++) {
            if (!intArray[i].equals(null)) {
                newIntArray[j] = intArray[i];
                j++;
            }
        }
        return newIntArray;
    }

    private void sortArray() {
        mergeSort(intArray);
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

    private boolean binarySearch(Integer item) {
        int min = 0;
        int max = intArray.length - 1;

        while (min <= max) {
            int mid = (min + max) / 2;

            if (item == intArray[mid]) {
                return true;
            }

            if (item < intArray[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return false;
    }

    private void grow() {
        Integer[] newIntArray = new Integer[intArray.length + intArray.length / 2];
        System.arraycopy(intArray, 0, newIntArray, 0, intArray.length);
        intArray = newIntArray;
    }

    private void resize(){
        Integer[] newIntArray = new Integer[intArray.length - intArray.length/3];
        System.arraycopy(intArray, 0, newIntArray, 0, intArray.length);
        intArray = newIntArray;
    }

}
