import java.util.Scanner;

public class NumberPyramid {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of rows: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            // Leading spaces for pyramid shape
            for (int space = 0; space < n - i - 1; space++) {
                System.out.print("   ");
            }

            int num = 1;

            for (int j = 0; j <= i; j++) {
                System.out.printf("%-6d", num);
                num = num * (i - j) / (j + 1);
            }

            System.out.println();
        }

        sc.close();
    }
}