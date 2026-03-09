package com.registration;

/**
 * Entry point for the Student Course Registration System.
 *
 * Initialises the EnrollmentManager, optionally loads saved data,
 * and launches the interactive console menu.
 */
public class Main {

    public static void main(String[] args) {
        EnrollmentManager manager = new EnrollmentManager();

        // Auto-load persisted data if available
        manager.loadAll();

        // Launch interactive menu
        Menu menu = new Menu(manager);
        menu.run();
    }
}
