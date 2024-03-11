package org.example;


public class Student {
        String name;
        int rollNumber;
        double marks;

        public Student(String name, int rollNumber, double marks) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.marks = marks;
        }

        public void display() {
            System.out.println("Name: " + name + "\nRoll Number: " + rollNumber + "\nMarks: " + marks);
        }
    }


