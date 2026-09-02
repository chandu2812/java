import java.util.*;

// ==========================================
// CUSTOM EXCEPTIONS
// ==========================================
class InvalidStudentException extends Exception {
    public InvalidStudentException(String message) {
        super(message);
    }
}

class CourseNotFoundException extends Exception {
    public CourseNotFoundException(String message) {
        super(message);
    }
}

class DuplicateRegistrationException extends Exception {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}

class CourseFullException extends Exception {
    public CourseFullException(String message) {
        super(message);
    }
}

// ==========================================
// CORE ENTITIES & POLYMORPHISM (CO1)
// ==========================================
abstract class Student {
    private String studentId;
    private String name;
    private String email;
    private List<Course> registeredCourses;

    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public abstract double calculateFee(double baseFee);

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "] ID: " + studentId + " | Name: " + name + " | Email: " + email;
    }
}

class UndergraduateStudent extends Student {
    public UndergraduateStudent(String studentId, String name, String email) {
        super(studentId, name, email);
    }

    @Override
    public double calculateFee(double baseFee) {
        return baseFee * 0.85; // 15% discount for Undergraduates
    }
}

class GraduateStudent extends Student {
    public GraduateStudent(String studentId, String name, String email) {
        super(studentId, name, email);
    }

    @Override
    public double calculateFee(double baseFee) {
        return baseFee; // Full rate for Graduate students
    }
}

// ==========================================
// COURSE & SYNCHRONIZATION (CO1, CO3)
// ==========================================
class Course {
    private String courseCode;
    private String title;
    private int capacity;
    private double baseFee;
    private Set<Student> enrolledStudents;
    private Queue<Student> waitlist;

    public Course(String courseCode, String title, int capacity, double baseFee) {
        this.courseCode = courseCode;
        this.title = title;
        this.capacity = capacity;
        this.baseFee = baseFee;
        this.enrolledStudents = new HashSet<>();
        this.waitlist = new LinkedList<>();
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getBaseFee() {
        return baseFee;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Queue<Student> getWaitlist() {
        return waitlist;
    }

    public synchronized boolean enrollStudent(Student student) throws DuplicateRegistrationException {
        if (enrolledStudents.contains(student) || waitlist.contains(student)) {
            throw new DuplicateRegistrationException(
                    "Student " + student.getStudentId() + " is already enrolled or waitlisted.");
        }

        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.getRegisteredCourses().add(this);
            return true; // Direct enrollment
        } else {
            waitlist.add(student);
            return false; // Added to waitlist
        }
    }

    public synchronized Student dropStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            student.getRegisteredCourses().remove(this);
            if (!waitlist.isEmpty()) {
                Student promotedStudent = waitlist.poll();
                enrolledStudents.add(promotedStudent);
                promotedStudent.getRegisteredCourses().add(this);
                notifyAll(); // Inter-thread notification for state change
                return promotedStudent;
            }
        } else {
            waitlist.remove(student);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("%s - %s | Enrolled: %d/%d | Waitlist: %d | Fee: $%.2f",
                courseCode, title, enrolledStudents.size(), capacity, waitlist.size(), baseFee);
    }
}

// ==========================================
// NOTIFICATION SYSTEM (CO1, CO3)
// ==========================================
abstract class Notification {
    private String notificationId;
    private String recipientEmail;
    private String message;

    public Notification(String notificationId, String recipientEmail, String message) {
        this.notificationId = notificationId;
        this.recipientEmail = recipientEmail;
        this.message = message;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public String getMessage() {
        return message;
    }

    public abstract void send();
}

class EmailNotification extends Notification {
    public EmailNotification(String notificationId, String recipientEmail, String message) {
        super(notificationId, recipientEmail, message);
    }

    @Override
    public void send() {
        System.out.println("[EMAIL OUTBOX] To: " + getRecipientEmail() + " | Body: " + getMessage());
    }
}

// ==========================================
// MULTITHREADING & CONCURRENCY WORKER (CO3)
// ==========================================
class ConcurrentRegistrationTask implements Runnable {
    private Student student;
    private Course course;
    private List<Notification> notificationLog;

    public ConcurrentRegistrationTask(Student student, Course course, List<Notification> notificationLog) {
        this.student = student;
        this.course = course;
        this.notificationLog = notificationLog;
    }

    @Override
    public void run() {
        try {
            boolean status = course.enrollStudent(student);
            String msg;
            if (status) {
                msg = "Successfully enrolled in " + course.getCourseCode() + " (" + course.getTitle() + ")";
            } else {
                msg = "Course full. Added to waitlist for " + course.getCourseCode();
            }
            Notification note = new EmailNotification("N-" + System.currentTimeMillis(), student.getEmail(), msg);
            synchronized (notificationLog) {
                notificationLog.add(note);
            }
            note.send();
        } catch (DuplicateRegistrationException e) {
            System.err.println("[Thread Alert] " + e.getMessage());
        }
    }
}

// ==========================================
// MAIN DRIVER SYSTEM (CO1, CO2, CO3)
// ==========================================
public class SmartUniversitySystem {
    private Map<String, Student> studentsMap = new HashMap<>();
    private Map<String, Course> coursesMap = new HashMap<>();
    private List<Notification> notificationsList = Collections.synchronizedList(new ArrayList<>());
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SmartUniversitySystem system = new SmartUniversitySystem();
        system.seedData();
        system.runMenu();
    }

    private void seedData() {
        studentsMap.put("S101", new UndergraduateStudent("S101", "Alice Smith", "alice@univ.edu"));
        studentsMap.put("S102", new GraduateStudent("S102", "Bob Jones", "bob@univ.edu"));
        studentsMap.put("S103", new UndergraduateStudent("S103", "Charlie Brown", "charlie@univ.edu"));

        coursesMap.put("CS101", new Course("CS101", "Java Programming", 2, 500.00));
        coursesMap.put("CS102", new Course("CS102", "Data Structures", 1, 600.00));
    }

    public void runMenu() {
        while (true) {
            System.out.println("\n=== SMART UNIVERSITY REGISTRATION SYSTEM ===");
            System.out.println("1. Register New Student");
            System.out.println("2. Add New Course");
            System.out.println("3. Display Available Courses");
            System.out.println("4. Enroll Student in Course");
            System.out.println("5. Drop Student from Course");
            System.out.println("6. Search & Display Student Record");
            System.out.println("7. Run Concurrent Registration Simulation (Multithreading)");
            System.out.println("8. Generate System Utilization Report");
            System.out.println("9. Exit");
            System.out.print("Select an option (1-9): ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addStudent();
                    case 2 -> addCourse();
                    case 3 -> displayCourses();
                    case 4 -> enrollStudentUI();
                    case 5 -> dropStudentUI();
                    case 6 -> searchStudent();
                    case 7 -> runMultithreadingSimulation();
                    case 8 -> generateReport();
                    case 9 -> {
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please select 1-9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Input must be a numerical value.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addStudent() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Type (1 for Undergrad, 2 for Graduate): ");
        int type = Integer.parseInt(scanner.nextLine());

        Student s = (type == 1) ? new UndergraduateStudent(id, name, email) : new GraduateStudent(id, name, email);
        studentsMap.put(id, s);
        System.out.println("Student registered successfully!");
    }

    private void addCourse() {
        System.out.print("Course Code: ");
        String code = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Capacity: ");
        int cap = Integer.parseInt(scanner.nextLine());
        System.out.print("Base Fee: ");
        double fee = Double.parseDouble(scanner.nextLine());

        coursesMap.put(code, new Course(code, title, cap, fee));
        System.out.println("Course created successfully!");
    }

    private void displayCourses() {
        System.out.println("\n--- Course Catalog ---");
        Iterator<Course> iterator = coursesMap.values().iterator(); // CO2 Traversal
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    private void enrollStudentUI()
            throws InvalidStudentException, CourseNotFoundException, DuplicateRegistrationException {
        System.out.print("Enter Student ID: ");
        String sId = scanner.nextLine();
        Student student = studentsMap.get(sId);
        if (student == null)
            throw new InvalidStudentException("Student ID not found.");

        System.out.print("Enter Course Code: ");
        String cCode = scanner.nextLine();
        Course course = coursesMap.get(cCode);
        if (course == null)
            throw new CourseNotFoundException("Course Code not found.");

        boolean enrolled = course.enrollStudent(student);
        if (enrolled) {
            System.out.println("Successfully enrolled!");
        } else {
            System.out.println("Course full! Added to waitlist.");
        }
    }

    private void dropStudentUI() throws InvalidStudentException, CourseNotFoundException {
        System.out.print("Enter Student ID: ");
        String sId = scanner.nextLine();
        Student student = studentsMap.get(sId);
        if (student == null)
            throw new InvalidStudentException("Student ID not found.");

        System.out.print("Enter Course Code: ");
        String cCode = scanner.nextLine();
        Course course = coursesMap.get(cCode);
        if (course == null)
            throw new CourseNotFoundException("Course Code not found.");

        Student promoted = course.dropStudent(student);
        System.out.println("Student dropped from course.");
        if (promoted != null) {
            System.out.println("Waitlisted student promoted: " + promoted.getName());
        }
    }

    private void searchStudent() throws InvalidStudentException {
        System.out.print("Enter Student ID: ");
        String sId = scanner.nextLine();
        Student student = studentsMap.get(sId);
        if (student == null)
            throw new InvalidStudentException("Student ID does not exist.");

        System.out.println("\n--- Student Details ---");
        System.out.println(student);
        System.out.println("Enrolled Courses:");
        for (Course c : student.getRegisteredCourses()) {
            System.out.println(
                    " - " + c.getCourseCode() + " (Calculated Fee: $" + student.calculateFee(c.getBaseFee()) + ")");
        }
    }

    private void runMultithreadingSimulation() {
        System.out.println("\n--- Starting Multithreaded Registration Simulation ---");
        Course cs101 = coursesMap.get("CS101");

        // Concurrent registration requests for same course (Capacity = 2)
        Student tS1 = new UndergraduateStudent("S901", "Simulated User 1", "u1@test.com");
        Student tS2 = new GraduateStudent("S902", "Simulated User 2", "u2@test.com");
        Student tS3 = new UndergraduateStudent("S903", "Simulated User 3", "u3@test.com");

        Thread thread1 = new Thread(new ConcurrentRegistrationTask(tS1, cs101, notificationsList));
        Thread thread2 = new Thread(new ConcurrentRegistrationTask(tS2, cs101, notificationsList));
        Thread thread3 = new Thread(new ConcurrentRegistrationTask(tS3, cs101, notificationsList));

        // Priority Configuration (CO3)
        thread1.setPriority(Thread.MAX_PRIORITY); // High priority
        thread2.setPriority(Thread.NORM_PRIORITY);
        thread3.setPriority(Thread.MIN_PRIORITY);

        thread1.start();
        thread2.start();
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            System.err.println("Thread simulation interrupted.");
        }
        System.out.println("Simulation execution complete.");
    }

    private void generateReport() {
        System.out.println("\n=============================================");
        System.out.println("        SYSTEM UTILIZATION REPORT           ");
        System.out.println("=============================================");
        System.out.println("Total Registered Students: " + studentsMap.size());
        System.out.println("Total Available Courses  : " + coursesMap.size());
        System.out.println("---------------------------------------------");

        for (Course course : coursesMap.values()) {
            double utilization = ((double) course.getEnrolledStudents().size() / course.getCapacity()) * 100;
            System.out.printf("Course: %-8s | Capacity: %d | Enrolled: %d | Waitlisted: %d | Utilization: %.1f%%\n",
                    course.getCourseCode(), course.getCapacity(), course.getEnrolledStudents().size(),
                    course.getWaitlist().size(), utilization);
        }
        System.out.println("=============================================\n");
    }
}