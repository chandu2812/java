import java.util.Scanner;

public class SkipNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("M = ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid M value.");
            return;
        }
        int m = sc.nextInt();

        System.out.print("N = ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid N value.");
            return;
        }
        int n = sc.nextInt();

        System.out.print("K = ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid K value.");
            return;
        }
        int k = sc.nextInt();

        if (m > n) {
            System.out.println("M must be less than or equal to N.");
            return;
        }

        if (k < 0) {
            System.out.println("K cannot be negative.");
            return;
        }

        int step = k + 1;

        for (int i = m; i <= n; i += step) {
            System.out.print(i);
            if (i + step <= n) {
                System.out.print(", ");
            }
        }
    }
}