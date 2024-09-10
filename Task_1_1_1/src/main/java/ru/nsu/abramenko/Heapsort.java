package ru.nsu.abramenko;

/** Класс пирамидальной сортировки с функциями heartsort и heapify.
 *
 * @author Абраменко Владимир
 */
public class Heapsort {
    /** Преобразование кучи относительно некоторого элемента.
     *
     * @param arr
     * @param n
     * @param i
     */
    private static void heapify(int[] arr, int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        if (l < n && arr[l] > arr[largest]) {
            largest = l;
        }
        if (r < n && arr[r] > arr[largest]) {
            largest = r;
        }
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
            heapify(arr, n, largest);
        }
    }

    /** Сортировка массива, чрезе создание кучи и перестраивание через heapify.
     *
     * @param arr
     * @return sortea array
     */
    public static int[] heapsort(int[] arr) {
        int n = arr.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }
        for (int i = n-1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
            heapify(arr, i, 0);
        }
        return arr;
    }

    /** main.
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i : heapsort(new int[] {5, 4, 3, 2, 1})) {
            System.out.print(i + " ");
        }
        System.out.print("\n");
    }
}
