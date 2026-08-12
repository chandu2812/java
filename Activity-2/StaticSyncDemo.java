class ExamCounter {
    static int counter = 0;

    static synchronized void updateCounter() {
        counter++;
        System.out.println(Thread.currentThread().getName() +
                " Counter: " + counter);
    }
}

class CounterThread extends Thread {
    public CounterThread(String name) {
        super(name);
    }

    public void run() {
        ExamCounter.updateCounter();
    }
}

class StaticSyncDemo {
    public static void main(String[] args) throws Exception {
        CounterThread t1 = new CounterThread("Student1");
        CounterThread t2 = new CounterThread("Student2");
        CounterThread t3 = new CounterThread("Student3");

        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Final Counter Value: " + ExamCounter.counter);
    }
}