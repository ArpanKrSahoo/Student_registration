package com.registration;

import java.util.Scanner;

/**
 * Console-based menu interface for the Student Course Registration System.
 * Delegates all business logic to EnrollmentManager.
 */
public class Menu {

    private final EnrollmentManager manager;
    private final Scanner scanner;

    public Menu(EnrollmentManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    /** Entry point — displays the main menu in a loop until the user exits. */
    public void run() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║   Student Course Registration System     ║");
        System.out.println("╚══════════════════════════════════════════╝");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            System.out.println();
            try {
                switch (choice) {
                    case 1  -> handleAddStudent();
                    case 2  -> handleAddCourse();
                    case 3  -> handleAddInstructor();
                    case 4  -> handleAssignInstructor();
                    case 5  -> handleRegisterStudent();
                    case 6  -> handleDropCourse();
                    case 7  -> handleViewTimetable();
                    case 8  -> manager.listAllStudents();
                    case 9  -> manager.listAllCourses();
                    case 10 -> manager.saveAll();
                    case 11 -> manager.loadAll();
                    case 0  -> { running = false; System.out.println("Goodbye!"); }
                    default -> System.out.println("[!] Invalid option. Please try again.");
                }
            } catch (RegistrationException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        scanner.close();
    }

    // ── Menu Handlers ────────────────────────────────────────────────────────

    private void handleAddStudent() throws RegistrationException {
        System.out.println("── Add Student ──────────────────");
        String id    = readString("Student ID : ");
        String name  = readString("Name       : ");
        String email = readString("Email      : ");
        manager.addStudent(id, name, email);
    }

    private void handleAddCourse() throws RegistrationException {
        System.out.println("── Add Course ───────────────────");
        String id       = readString("Course ID    : ");
        String name     = readString("Course Name  : ");
        int    capacity = readInt("Max Capacity : ");
        manager.addCourse(id, name, capacity);
    }

    private void handleAddInstructor() throws RegistrationException {
        System.out.println("── Add Instructor ───────────────");
        String id   = readString("Instructor ID : ");
        String name = readString("Name          : ");
        String dept = readString("Department    : ");
        manager.addInstructor(id, name, dept);
    }

    private void handleAssignInstructor() throws RegistrationException {
        System.out.println("── Assign Instructor ────────────");
        String instId   = readString("Instructor ID : ");
        String courseId = readString("Course ID     : ");
        manager.assignInstructor(instId, courseId);
    }

    private void handleRegisterStudent() throws RegistrationException {
        System.out.println("── Register Student for Course ──");
        String studentId = readString("Student ID : ");
        String courseId  = readString("Course ID  : ");
        manager.registerStudentForCourse(studentId, courseId);
    }

    private void handleDropCourse() throws RegistrationException {
        System.out.println("── Drop Course ──────────────────");
        String studentId = readString("Student ID : ");
        String courseId  = readString("Course ID  : ");
        manager.dropStudentFromCourse(studentId, courseId);
    }

    private void handleViewTimetable() throws RegistrationException {
        String studentId = readString("Student ID : ");
        manager.viewStudentTimetable(studentId);
    }

    // ── Print Helpers ────────────────────────────────────────────────────────

    private void printMainMenu() {
        System.out.println("\n┌─────────────────────────────────────────┐");
        System.out.println("│               MAIN MENU                 │");
        System.out.println("├──────────────────────────────────────────┤");
        System.out.println("│  1.  Add Student                         │");
        System.out.println("│  2.  Add Course                          │");
        System.out.println("│  3.  Add Instructor                      │");
        System.out.println("│  4.  Assign Instructor to Course         │");
        System.out.println("│  5.  Register Student for Course         │");
        System.out.println("│  6.  Drop Course                         │");
        System.out.println("│  7.  View Student Timetable              │");
        System.out.println("│  8.  List All Students                   │");
        System.out.println("│  9.  List All Courses                    │");
        System.out.println("│  10. Save Data                           │");
        System.out.println("│  11. Load Data                           │");
        System.out.println("│  0.  Exit                                │");
        System.out.println("└──────────────────────────────────────────┘");
    }

    private String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
            }
        }
    }
}
