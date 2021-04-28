import java.util.Arrays;

class Quick {

    public static void main(String... args) {
        int[] arr = {9, 8, 8, 5, 7, 7, 4, 4, 4, 2};
		System.out.println(Arrays.toString(arr));
		quickSort(arr);
		System.out.println(Arrays.toString(arr));
	}


    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    private static void quickSort(int[] arr, int first, int last) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, first, last + 1)));
        if(first >= last)
            return;
        int split = partition(arr, first, last);
        quickSort(arr, first, split - 1);
        quickSort(arr, split + 1, last);
    }

    private static int partition(int[] arr, int first, int last) {
        int pivot = arr[last];
        int i = first - 1;
        int j = last + 1;
        while(true) {
            do {
                i++;
            } while(arr[i] < pivot);

            do {
                j--;
            } while(arr[j] > pivot);

            if(i < j)
                swap(arr, i, j);
            else return j;
        }
    }

    private static void swap(int[] arr, int a, int b) {
        final int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


}
