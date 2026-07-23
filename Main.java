import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int arr1[] = { 1, 3, 4, 5 };
        int arr2[] = { 2, 4, 6, 8 };
        ArrayList<Integer> arr3 = new ArrayList<>();
        int i = 0, j = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                arr3.add(arr1[i]);
                i++;
            } else {
                arr3.add(arr2[j]);
                j++;
            }
        }

        while (i < arr1.length) {
            arr3.add(arr1[i]);
            i++;
        }

        while (j < arr2.length) {
            arr3.add(arr2[j]);
            j++;
        }

        System.out.println("Merged Array: " + arr3);
    }
}