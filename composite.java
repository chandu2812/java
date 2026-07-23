public class composite{
    public static void main(String[] args) {

        int[] arr = { 16, 18, 27, 16, 23, 21, 19 };

        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            int n = arr[i];

            if (n > 1) {
                int factors = 0;

                for (int j = 1; j <= n; j++) {
                    if (n % j == 0)
                        factors++;
                }

                if (factors > 2)
                    count++;
            }
        }

        System.out.println("Number of Composite Numbers = " + count);
    }
}