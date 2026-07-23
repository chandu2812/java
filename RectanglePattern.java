import java.util.Scanner;

class RectanglePattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter symbol: ");
        char symbol = sc.next().charAt(0);

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 5; j++) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
    }
}