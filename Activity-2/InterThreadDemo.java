import java.util.Scanner;

class Customer {
    int accountNo = 101;
    String accName = "Chandu";
    int balance = 1000;

    synchronized void withdraw(int amount) {
        System.out.println("Withdraw Request: " + amount);

        if (balance < amount) {
            System.out.println("Insufficient balance. Waiting for deposit...");
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }

        balance -= amount;
        System.out.println("Withdraw Successful. Balance: " + balance);
    }

    synchronized void deposit(int amount) {
        System.out.println("Deposited: " + amount);
        balance += amount;
        System.out.println("Balance after deposit: " + balance);
        notify();
    }
}

public class InterThreadDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Customer c = new Customer();

        System.out.print("Enter withdraw amount: ");
        int w = sc.nextInt();

        Thread t1 = new Thread(() -> c.withdraw(w));
        t1.start();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }

        System.out.print("Enter deposit amount: ");
        int d = sc.nextInt();

        Thread t2 = new Thread(() -> c.deposit(d));
        t2.start();
    }
}