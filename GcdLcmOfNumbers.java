import java.util.Scanner;

public class GcdLcmOfNumbers {
    static long gcd(long a, long b) {
        a = Math.abs(a);
        b = Math.abs(b);

        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    static long lcm(long a, long b) {
        if (a == 0 || b == 0)
            return 0;
        return Math.abs(a * b) / gcd(a, b);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("N value = ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid N value.");
            return;
        }

        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("N must be greater than 0.");
            return;
        }

        long gcdResult = 0;
        long lcmResult = 1;

        for (int i = 1; i <= n; i++) {
            System.out.print("Number " + i + " = ");

            if (!sc.hasNextLong()) {
                System.out.println("Invalid number entered.");
                return;
            }

            long number = sc.nextLong();
            gcdResult = gcd(gcdResult, number);
            lcmResult = lcm(lcmResult, number);
        }

        System.out.println("LCM = " + lcmResult);
        System.out.println("GCD = " + gcdResult);
    }
}