import java.util.Scanner;

public class VotingEligibility {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your age: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a whole-number age.");
            return;
        }

        int age = sc.nextInt();

        if (age < 0) {
            System.out.println("Age cannot be negative.");
        } else if (age >= 18) {
            System.out.println("You are eligible to vote.");
        } else {
            System.out.println("You are allowed to vote after " + (18 - age) + " years");
        }
    }
}