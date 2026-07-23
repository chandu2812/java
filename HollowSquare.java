import java.util.Scanner;

class HollowSquare {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter symbol: ");
        char symbol = sc.next().charAt(0);

        System.out.print("Enter size: ");
        int n = sc.nextInt();

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i == 1 || i == n || j == 1 || j == n) {
                    System.out.print(symbol + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}