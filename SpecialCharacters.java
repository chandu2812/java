import java.util.Scanner;

public class SpecialCharacters {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a line: ");
        String str = sc.nextLine();

        int count = 0;

        System.out.print("Special Characters: ");
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (!Character.isLetterOrDigit(ch) && ch != ' ') {
                System.out.print(ch + " ");
                count++;
            }
        }

        System.out.println("\nNumber of Special Characters = " + count);
    }
}