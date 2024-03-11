package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final String DB_URL = "jdbc:sqlite:Student.db";
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS students (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name TEXT NOT NULL," +
            "roll_number INTEGER NOT NULL," +
            "marks REAL NOT NULL)";
    private static final String INSERT_QUERY = "INSERT INTO students (name, roll_number, marks) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT * FROM students";

    public static void main(String[] args) {
        initializeDatabase();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                displayMenu();
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        addStudent(scanner);
                        break;
                    case 2:
                        viewStudents();
                        break;
                    case 3:
                        exitProgram();
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to initialize the database
    private static void initializeDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement createTableStatement = connection.prepareStatement(CREATE_TABLE_QUERY)) {

            createTableStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add a student
    private static void addStudent(Scanner scanner) {
        try {
            System.out.print("Enter student name: ");
            String name = scanner.next();

            System.out.print("Enter roll number: ");
            int rollNumber;
            while (true) {
                try {
                    rollNumber = scanner.nextInt();
                    break;
                } catch (java.util.InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid integer for the roll number.");
                    scanner.next(); // Consume the invalid input to avoid an infinite loop
                }
            }

            System.out.print("Enter marks: ");
            double marks = scanner.nextDouble();

            saveStudentToDatabase(name, rollNumber, marks);

            System.out.println("Student added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to save student information to the database
    private static void saveStudentToDatabase(String name, int rollNumber, double marks) {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement insertStatement = connection.prepareStatement(INSERT_QUERY)) {

            insertStatement.setString(1, name);
            insertStatement.setInt(2, rollNumber);
            insertStatement.setDouble(3, marks);
            insertStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to view all students
    private static void viewStudents() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int rollNumber = resultSet.getInt("roll_number");
                double marks = resultSet.getDouble("marks");

                displayStudent(name, rollNumber, marks);
                System.out.println("--------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to display student information
    private static void displayStudent(String name, int rollNumber, double marks) {
        System.out.println("Name: " + name + "\nRoll Number: " + rollNumber + "\nMarks: " + marks);
    }

    // Method to display the main menu
    private static void displayMenu() {
        System.out.println("1. Add Student\n2. View Students\n3. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method to exit the program
    private static void exitProgram() {
        System.out.println("Exiting program. Goodbye!");
        System.exit(0);
    }
}
