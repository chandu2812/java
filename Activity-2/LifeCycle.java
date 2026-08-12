class LifeCycle extends Thread {
    public void run() {
        try {
            System.out.println("Thread is Running");
            Thread.sleep(1000); // Waiting state
            System.out.println("Thread resumed after sleep");
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception {
        LifeCycle t = new LifeCycle();

        System.out.println("State after creation: " + t.getState()); // NEW

        t.start();
        System.out.println("State after start: " + t.getState()); // RUNNABLE

        Thread.sleep(100);
        System.out.println("State during execution: " + t.getState());

        t.join();
        System.out.println("State after completion: " + t.getState()); // TERMINATED
    }
}
