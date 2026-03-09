package com.registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a student in the Course Registration System.
 * Demonstrates encapsulation via private fields and public getters/setters.
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    private String studentId;
    private String name;
    private String email;
    private List<String> registeredCourseIds;

    public Student(String studentId, String name, String email) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.registeredCourseIds = new ArrayList<>();
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<String> getRegisteredCourseIds() {
        return new ArrayList<>(registeredCourseIds); // defensive copy
    }

    // ── Business Methods ─────────────────────────────────────────────────────

    /**
     * Registers the student for a course by course ID.
     * @return true if added, false if already registered
     */
    public boolean registerCourse(String courseId) {
        if (registeredCourseIds.contains(courseId)) return false;
        registeredCourseIds.add(courseId);
        return true;
    }

    /**
     * Drops a course from this student's registration list.
     * @return true if removed, false if not found
     */
    public boolean dropCourse(String courseId) {
        return registeredCourseIds.remove(courseId);
    }

    public boolean isRegisteredFor(String courseId) {
        return registeredCourseIds.contains(courseId);
    }

    public void viewRegisteredCourses() {
        if (registeredCourseIds.isEmpty()) {
            System.out.println("  [No courses registered]");
        } else {
            for (String id : registeredCourseIds) {
                System.out.println("  - " + id);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Student[id=%s, name=%s, email=%s, courses=%d]",
                studentId, name, email, registeredCourseIds.size());
    }
}
