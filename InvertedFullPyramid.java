import java.util.Scanner;

class InvertedFullPyramid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int n = sc.nextInt();

        for (int i = n; i >= 1; i--) {
            // Print spaces
            for (int space = 1; space <= n - i; space++) {
                System.out.print(" ");
            }

            // Print stars
            for (int star = 1; star <= 2 * i - 1; star++) {
                System.out.print("*");
            }

            System.out.println();
        }
    }
}