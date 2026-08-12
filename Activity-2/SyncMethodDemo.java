class BankAccount {
    int balance = 1000;

    synchronized void deposit(int amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + " Balance: " + balance);
    }

    synchronized void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + " Balance: " + balance);
        } else {
            System.out.println("Insufficient Balance");
        }
    }
}

class SyncMethodDemo {
    public static void main(String[] args) throws Exception {
        BankAccount acc = new BankAccount();

        Thread t1 = new Thread(() -> acc.deposit(500));
        Thread t2 = new Thread(() -> acc.withdraw(700));

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Final Balance: " + acc.balance);
    }
}