import java.util.Scanner;

public class SimpleInterest {
    static double calculateInterest(double principal, int years, boolean seniorCitizen) {
        double rate = seniorCitizen ? 12 : 10;
        return (principal * years * rate) / 100;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the principal amount: ");
        if (!sc.hasNextDouble()) {
            System.out.println("Invalid principal amount.");
            return;
        }
        double principal = sc.nextDouble();

        System.out.print("Enter the no of years: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid number of years.");
            return;
        }
        int years = sc.nextInt();

        if (principal < 0 || years < 0) {
            System.out.println("Principal and years cannot be negative.");
            return;
        }

        System.out.print("Is customer senior citizen (y/n): ");
        char choice = sc.next().toLowerCase().charAt(0);

        if (choice != 'y' && choice != 'n') {
            System.out.println("Enter only y or n.");
            return;
        }

        double interest = calculateInterest(principal, years, choice == 'y');
        System.out.println("Interest: " + interest);
    }
}