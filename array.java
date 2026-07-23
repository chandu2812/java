import java.util.Arrays;

public class array{
    public static void main(String[] args) {
        int[] arr = { 16, 18, 27, 16, 23, 21, 19 };
        int sum = 0;
        for (int i = 0; i < arr.length; i++)
            sum += arr[i];
        int mean = sum / arr.length;
        Arrays.sort(arr);
        int median = arr[arr.length / 2];
        int mode = arr[0], maxCount = 0;
        for (int i = 0; i < arr.length; i++) {
            int count = 1;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] == arr[j])
                    count++;
            }
            if (count > maxCount) {
                maxCount = count;
                mode = arr[i];
            }
        }
        System.out.println("Mean = " + mean);
        System.out.println("Median = " + median);
        System.out.println("Mode = " + mode);
    }
}