import java.util.Scanner;

class CharacterPattern {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the character to be printed: ");
        char ch = sc.next().charAt(0);

        System.out.print("Max number of times printed: ");
        int max = sc.nextInt();

        for (int i = 1; i <= max; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(ch + " ");
            }
            System.out.println();
        }
    }
}   