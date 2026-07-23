import java.util.Scanner;

class ReverseBinary {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a decimal number: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input. Enter a whole number only.");
            return;
        }

        int decimal = sc.nextInt();

        if (decimal < 0) {
            System.out.println("Invalid input. Enter a positive number.");
            return;
        }

        String binary = Integer.toBinaryString(decimal);
        String reverseBinary = "";

        for (int i = binary.length() - 1; i >= 0; i--) {
            reverseBinary = reverseBinary + binary.charAt(i);
        }

        int result = Integer.parseInt(reverseBinary, 2);

        System.out.println("Binary: " + binary);
        System.out.println("Reverse Binary: " + reverseBinary);
        System.out.println("Output: " + result);
    }
}