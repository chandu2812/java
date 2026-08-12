class TicketBooking {
    int tickets = 5;

    void bookTicket(String name, int count) {
        synchronized (this) {
            if (tickets >= count) {
                System.out.println(name + " booked " + count + " ticket(s)");
                tickets -= count;
                System.out.println("Remaining Tickets: " + tickets);
            } else {
                System.out.println(name + " booking failed");
            }
        }
    }
}

class SyncBlockDemo {
    public static void main(String[] args) {
        TicketBooking t = new TicketBooking();

        Thread t1 = new Thread(() -> t.bookTicket("Alice", 2));
        Thread t2 = new Thread(() -> t.bookTicket("Bob", 3));
        Thread t3 = new Thread(() -> t.bookTicket("Charlie", 1));

        t1.start();
        t2.start();
        t3.start();
    }
}