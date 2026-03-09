package com.registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a course instructor in the registration system.
 */
public class Instructor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String instructorId;
    private String name;
    private String department;
    private List<String> assignedCourseIds;

    public Instructor(String instructorId, String name, String department) {
        this.instructorId = instructorId;
        this.name = name;
        this.department = department;
        this.assignedCourseIds = new ArrayList<>();
    }

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public List<String> getAssignedCourseIds() {
        return new ArrayList<>(assignedCourseIds);
    }

    // ── Business Methods ─────────────────────────────────────────────────────

    /**
     * Assigns a course to this instructor.
     * @return true if assigned, false if already assigned
     */
    public boolean assignCourse(String courseId) {
        if (assignedCourseIds.contains(courseId)) return false;
        assignedCourseIds.add(courseId);
        return true;
    }

    /**
     * Removes a course assignment from this instructor.
     */
    public boolean removeCourse(String courseId) {
        return assignedCourseIds.remove(courseId);
    }

    public void displayDetails() {
        System.out.println("─────────────────────────────────");
        System.out.println("Instructor ID : " + instructorId);
        System.out.println("Name          : " + name);
        System.out.println("Department    : " + department);
        System.out.println("Courses       : " + assignedCourseIds);
        System.out.println("─────────────────────────────────");
    }

    @Override
    public String toString() {
        return String.format("Instructor[id=%s, name=%s, dept=%s]",
                instructorId, name, department);
    }
}
