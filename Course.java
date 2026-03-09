package com.registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic course in the registration system.
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    private String courseId;
    private String courseName;
    private String instructorId;
    private int maxCapacity;
    private List<String> enrolledStudentIds;

    public Course(String courseId, String courseName, int maxCapacity) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
        this.enrolledStudentIds = new ArrayList<>();
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getCourseId() { return courseId; }
    public void setCourseId(String courseId) { this.courseId = courseId; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public List<String> getEnrolledStudentIds() {
        return new ArrayList<>(enrolledStudentIds);
    }

    public int getAvailableSeats() {
        return maxCapacity - enrolledStudentIds.size();
    }

    public boolean isFull() {
        return enrolledStudentIds.size() >= maxCapacity;
    }

    // ── Business Methods ─────────────────────────────────────────────────────

    /**
     * Adds a student to this course.
     * @return true if added, false if already enrolled or course is full
     */
    public boolean addStudent(String studentId) {
        if (isFull() || enrolledStudentIds.contains(studentId)) return false;
        enrolledStudentIds.add(studentId);
        return true;
    }

    /**
     * Removes a student from this course.
     * @return true if removed, false if student was not enrolled
     */
    public boolean removeStudent(String studentId) {
        return enrolledStudentIds.remove(studentId);
    }

    public boolean isStudentEnrolled(String studentId) {
        return enrolledStudentIds.contains(studentId);
    }

    public void displayDetails() {
        System.out.println("─────────────────────────────────");
        System.out.println("Course ID    : " + courseId);
        System.out.println("Course Name  : " + courseName);
        System.out.println("Instructor   : " + (instructorId != null ? instructorId : "TBA"));
        System.out.println("Capacity     : " + enrolledStudentIds.size() + "/" + maxCapacity);
        System.out.println("Available    : " + getAvailableSeats() + " seat(s)");
        System.out.println("─────────────────────────────────");
    }

    @Override
    public String toString() {
        return String.format("Course[id=%s, name=%s, capacity=%d/%d]",
                courseId, courseName, enrolledStudentIds.size(), maxCapacity);
    }
}
