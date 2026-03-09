package com.registration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Central controller for the Student Course Registration System.
 *
 * Manages:
 *  - Student and course records (HashMap storage)
 *  - Enrollment / drop logic with full validation
 *  - Instructor assignment to courses
 *  - Persistence via DataStore
 */
public class EnrollmentManager {

    private Map<String, Student>    students;
    private Map<String, Course>     courses;
    private Map<String, Instructor> instructors;

    public EnrollmentManager() {
        students    = new HashMap<>();
        courses     = new HashMap<>();
        instructors = new HashMap<>();
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Student Operations
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Adds a new student to the system.
     * @throws RegistrationException if the ID already exists or input is blank
     */
    public void addStudent(String id, String name, String email) throws RegistrationException {
        validateNotBlank(id, "Student ID");
        validateNotBlank(name, "Name");
        validateNotBlank(email, "Email");
        if (students.containsKey(id)) throw RegistrationException.duplicateStudent(id);

        students.put(id, new Student(id, name, email));
        System.out.println("[OK] Added student: " + name + " (" + id + ")");
    }

    /**
     * Returns a student by ID.
     * @throws RegistrationException if not found
     */
    public Student getStudent(String id) throws RegistrationException {
        Student s = students.get(id);
        if (s == null) throw RegistrationException.studentNotFound(id);
        return s;
    }

    /** Prints all registered students. */
    public void listAllStudents() {
        if (students.isEmpty()) { System.out.println("No students in the system."); return; }
        System.out.println("\n=== All Students ===");
        students.values().forEach(System.out::println);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Course Operations
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Adds a new course to the system.
     * @throws RegistrationException if the ID already exists or capacity is invalid
     */
    public void addCourse(String id, String name, int capacity) throws RegistrationException {
        validateNotBlank(id, "Course ID");
        validateNotBlank(name, "Course name");
        if (capacity <= 0) throw RegistrationException.invalidInput("Capacity must be > 0");
        if (courses.containsKey(id)) throw RegistrationException.duplicateCourse(id);

        courses.put(id, new Course(id, name, capacity));
        System.out.println("[OK] Added course: " + name + " (" + id + ")");
    }

    /**
     * Returns a course by ID.
     * @throws RegistrationException if not found
     */
    public Course getCourse(String id) throws RegistrationException {
        Course c = courses.get(id);
        if (c == null) throw RegistrationException.courseNotFound(id);
        return c;
    }

    /** Prints all courses. */
    public void listAllCourses() {
        if (courses.isEmpty()) { System.out.println("No courses in the system."); return; }
        System.out.println("\n=== All Courses ===");
        courses.values().forEach(Course::displayDetails);
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Instructor Operations
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Adds a new instructor to the system.
     */
    public void addInstructor(String id, String name, String department) throws RegistrationException {
        validateNotBlank(id, "Instructor ID");
        validateNotBlank(name, "Name");
        if (instructors.containsKey(id))
            throw new RegistrationException(RegistrationException.Reason.INVALID_INPUT,
                    "Instructor already exists: " + id);

        instructors.put(id, new Instructor(id, name, department));
        System.out.println("[OK] Added instructor: " + name + " (" + id + ")");
    }

    /**
     * Assigns an instructor to a course.
     * @throws RegistrationException if either the instructor or course is not found
     */
    public void assignInstructor(String instructorId, String courseId) throws RegistrationException {
        Instructor instructor = instructors.get(instructorId);
        if (instructor == null) throw RegistrationException.instructorNotFound(instructorId);

        Course course = getCourse(courseId);
        course.setInstructorId(instructorId);
        instructor.assignCourse(courseId);
        System.out.println("[OK] Assigned " + instructor.getName() + " to " + course.getCourseName());
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Enrollment Operations
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Registers a student for a course.
     * Validates: student exists, course exists, not already registered, course not full.
     *
     * @throws RegistrationException on any validation failure
     */
    public void registerStudentForCourse(String studentId, String courseId) throws RegistrationException {
        Student student = getStudent(studentId);
        Course  course  = getCourse(courseId);

        if (student.isRegisteredFor(courseId))
            throw RegistrationException.duplicateRegistration(studentId, courseId);
        if (course.isFull())
            throw RegistrationException.courseFull(courseId);

        student.registerCourse(courseId);
        course.addStudent(studentId);
        System.out.println("[OK] " + student.getName() + " registered for " + course.getCourseName());
    }

    /**
     * Drops a student from a course.
     *
     * @throws RegistrationException if the student is not enrolled in the course
     */
    public void dropStudentFromCourse(String studentId, String courseId) throws RegistrationException {
        Student student = getStudent(studentId);
        Course  course  = getCourse(courseId);

        if (!student.isRegisteredFor(courseId))
            throw RegistrationException.notEnrolled(studentId, courseId);

        student.dropCourse(courseId);
        course.removeStudent(studentId);
        System.out.println("[OK] " + student.getName() + " dropped " + course.getCourseName());
    }

    /**
     * Displays all courses a student is registered for (timetable).
     */
    public void viewStudentTimetable(String studentId) throws RegistrationException {
        Student student = getStudent(studentId);
        System.out.println("\n=== Timetable for " + student.getName() + " ===");
        if (student.getRegisteredCourseIds().isEmpty()) {
            System.out.println("  [No courses registered]");
            return;
        }
        for (String cid : student.getRegisteredCourseIds()) {
            Course c = courses.get(cid);
            if (c != null) {
                String instructor = "TBA";
                if (c.getInstructorId() != null) {
                    Instructor ins = instructors.get(c.getInstructorId());
                    if (ins != null) instructor = ins.getName();
                }
                System.out.printf("  %-10s  %-30s  Instructor: %s%n",
                        c.getCourseId(), c.getCourseName(), instructor);
            }
        }
        System.out.println("══════════════════════════════════════");
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Persistence
    // ════════════════════════════════════════════════════════════════════════

    /** Saves all data to disk. */
    public void saveAll() {
        try {
            DataStore.saveStudents(students);
            DataStore.saveCourses(courses);
            DataStore.saveInstructors(instructors);
            System.out.println("[OK] All data saved successfully.");
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to save data: " + e.getMessage());
        }
    }

    /** Loads all data from disk. */
    public void loadAll() {
        try {
            students    = DataStore.loadStudents();
            courses     = DataStore.loadCourses();
            instructors = DataStore.loadInstructors();
            System.out.println("[OK] Data loaded — students: " + students.size()
                    + ", courses: " + courses.size()
                    + ", instructors: " + instructors.size());
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[ERROR] Failed to load data: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    //  Helpers
    // ════════════════════════════════════════════════════════════════════════

    private void validateNotBlank(String value, String fieldName) throws RegistrationException {
        if (value == null || value.isBlank())
            throw RegistrationException.invalidInput(fieldName + " cannot be blank");
    }
}
