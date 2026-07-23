import java.util.Scanner;

public class matrixSum {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] a = new int[2][2];
        int[][] b = new int[2][2];
        int[][] c = new int[2][2];
        System.out.println("Enter Matrix 1:");
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                a[i][j] = sc.nextInt();
        System.out.println("Enter Matrix 2:");
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                b[i][j] = sc.nextInt();
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 2; j++)
                c[i][j] = a[i][j] + b[i][j];
        System.out.println("Matrix Sum:");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++)
                System.out.print(c[i][j] + " ");
            System.out.println();
        }
    }
}
    

