import java.util.Scanner;

public class UserNameValidation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the user name: ");
        String user1 = sc.nextLine();

        System.out.print("Re-enter the user name: ");
        String user2 = sc.nextLine();

        if (user1.equals(user2)) {
            System.out.println("User name is Valid");
        } else {
            System.out.println("User name is Invalid");
        }
    }
}