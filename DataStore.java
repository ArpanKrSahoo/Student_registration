package com.registration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Handles serialization / deserialization of all system data to disk.
 * Each entity type is stored in its own file for simplicity.
 */
public class DataStore {

    private static final String STUDENTS_FILE    = "data/students.dat";
    private static final String COURSES_FILE     = "data/courses.dat";
    private static final String INSTRUCTORS_FILE = "data/instructors.dat";

    // ── Save ──────────────────────────────────────────────────────────────────

    public static void saveStudents(Map<String, Student> students) throws IOException {
        saveObject(students, STUDENTS_FILE);
        System.out.println("[DataStore] Students saved.");
    }

    public static void saveCourses(Map<String, Course> courses) throws IOException {
        saveObject(courses, COURSES_FILE);
        System.out.println("[DataStore] Courses saved.");
    }

    public static void saveInstructors(Map<String, Instructor> instructors) throws IOException {
        saveObject(instructors, INSTRUCTORS_FILE);
        System.out.println("[DataStore] Instructors saved.");
    }

    // ── Load ──────────────────────────────────────────────────────────────────

    @SuppressWarnings("unchecked")
    public static Map<String, Student> loadStudents() throws IOException, ClassNotFoundException {
        if (!new File(STUDENTS_FILE).exists()) return new HashMap<>();
        return (Map<String, Student>) loadObject(STUDENTS_FILE);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Course> loadCourses() throws IOException, ClassNotFoundException {
        if (!new File(COURSES_FILE).exists()) return new HashMap<>();
        return (Map<String, Course>) loadObject(COURSES_FILE);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Instructor> loadInstructors() throws IOException, ClassNotFoundException {
        if (!new File(INSTRUCTORS_FILE).exists()) return new HashMap<>();
        return (Map<String, Instructor>) loadObject(INSTRUCTORS_FILE);
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static void saveObject(Object obj, String path) throws IOException {
        new File("data").mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(obj);
        }
    }

    private static Object loadObject(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            return ois.readObject();
        }
    }
}
