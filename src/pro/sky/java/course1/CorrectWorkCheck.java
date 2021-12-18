package pro.sky.java.course1;

import java.util.Arrays;

public class CorrectWorkCheck {

    public static void main(String[] args) {
        final IntListImpl intList = new IntListImpl(10);

        int[] arr = generateRandomArray();
        int[] bubbleArr = Arrays.copyOf(arr, arr.length);
        int[] sortSelectionArr = Arrays.copyOf(arr, arr.length);
        int[] sortInsert = Arrays.copyOf(arr, arr.length);

        long start = System.currentTimeMillis();
        sortBubble(bubbleArr);
        System.out.println(System.currentTimeMillis() - start);

        long startSortSel = System.currentTimeMillis();
        sortSelection(sortSelectionArr);
        System.out.println(System.currentTimeMillis() - startSortSel);

        long startSortInsert = System.currentTimeMillis();
        sortInsertion(sortInsert);
        System.out.println(System.currentTimeMillis() - startSortInsert);


    }

    public static int[] generateRandomArray() {
        java.util.Random random = new java.util.Random();
        int[] arr = new int[100_000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100_000);
        }
        return arr;
    }

    private static void swapElements(int[] arr, int indexA, int indexB) {
        int tmp = arr[indexA];
        arr[indexA] = arr[indexB];
        arr[indexB] = tmp;
    }

    public static void sortBubble(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swapElements(arr, j, j + 1);
                }
            }
        }
    }

    public static void sortSelection(int[] arr) {
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

    public static void sortInsertion(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] >= temp) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = temp;
        }
    }

}
