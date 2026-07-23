import java.util.Scanner;

public class StringToInteger {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a String: ");
        String str = sc.nextLine();

        try {
            int num = Integer.parseInt(str);
            System.out.println("Output Integer: " + num);
        } catch (NumberFormatException e) {
            System.out.println("Invalid Integer");
        }
    }
}