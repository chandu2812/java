public class PriorityDemo extends Thread {
    public PriorityDemo(String name) {
        super(name);
    }

    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + " Priority: " + getPriority());
        }
    }

    public static void main(String[] args) {
        PriorityDemo high = new PriorityDemo("High");
        PriorityDemo medium = new PriorityDemo("Medium");
        PriorityDemo low = new PriorityDemo("Low");

        high.setPriority(Thread.MAX_PRIORITY);   // 10
        medium.setPriority(Thread.NORM_PRIORITY); // 5
        low.setPriority(Thread.MIN_PRIORITY);     // 1

        high.start();
        medium.start();
        low.start();
    }
} 