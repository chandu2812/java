import java.util.Scanner;

public class RemoveVowels {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String str = sc.nextLine();

        System.out.print("The string without vowels is: ");

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if ("AEIOUaeiou".indexOf(ch) == -1) {
                System.out.print(ch);
            }
        }
    }
}