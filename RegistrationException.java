package com.registration;

/**
 * Custom exception for all registration-related errors.
 * Provides meaningful messages for common failure scenarios.
 */
public class RegistrationException extends Exception {

    private static final long serialVersionUID = 1L;

    public enum Reason {
        STUDENT_NOT_FOUND,
        COURSE_NOT_FOUND,
        INSTRUCTOR_NOT_FOUND,
        DUPLICATE_STUDENT,
        DUPLICATE_COURSE,
        DUPLICATE_REGISTRATION,
        COURSE_FULL,
        NOT_ENROLLED,
        INVALID_INPUT
    }

    private final Reason reason;

    public RegistrationException(Reason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public Reason getReason() { return reason; }

    // ── Factory helpers ───────────────────────────────────────────────────────

    public static RegistrationException studentNotFound(String id) {
        return new RegistrationException(Reason.STUDENT_NOT_FOUND,
                "Student not found: " + id);
    }

    public static RegistrationException courseNotFound(String id) {
        return new RegistrationException(Reason.COURSE_NOT_FOUND,
                "Course not found: " + id);
    }

    public static RegistrationException instructorNotFound(String id) {
        return new RegistrationException(Reason.INSTRUCTOR_NOT_FOUND,
                "Instructor not found: " + id);
    }

    public static RegistrationException duplicateStudent(String id) {
        return new RegistrationException(Reason.DUPLICATE_STUDENT,
                "Student already exists: " + id);
    }

    public static RegistrationException duplicateCourse(String id) {
        return new RegistrationException(Reason.DUPLICATE_COURSE,
                "Course already exists: " + id);
    }

    public static RegistrationException duplicateRegistration(String studentId, String courseId) {
        return new RegistrationException(Reason.DUPLICATE_REGISTRATION,
                "Student " + studentId + " is already registered for course " + courseId);
    }

    public static RegistrationException courseFull(String courseId) {
        return new RegistrationException(Reason.COURSE_FULL,
                "Course " + courseId + " has reached maximum capacity");
    }

    public static RegistrationException notEnrolled(String studentId, String courseId) {
        return new RegistrationException(Reason.NOT_ENROLLED,
                "Student " + studentId + " is not enrolled in course " + courseId);
    }

    public static RegistrationException invalidInput(String message) {
        return new RegistrationException(Reason.INVALID_INPUT, message);
    }
}
