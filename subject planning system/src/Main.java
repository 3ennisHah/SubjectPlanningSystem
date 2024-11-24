import Data.Student;
import Data.Subject;
import Data.StudentRepository;
import Data.SubjectRepository;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        StudentRepository studentRepo = new StudentRepository();
        SubjectRepository subjectRepo = new SubjectRepository();

        // Example: Add predefined subjects to subjectRepo
        subjectRepo.saveSubject(Subject.CSC1024);
        subjectRepo.saveSubject(Subject.CSC1202);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Enroll in Subject");
            System.out.println("3. Drop Subject");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();

                    System.out.print("Enter Student Name: ");
                    String name = scanner.nextLine();

                    Student student = new Student(studentId, name);
                    studentRepo.saveStudent(student);
                    System.out.println("Student added successfully!");
                }
                case 2 -> {
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();

                    System.out.print("Enter Subject Code: ");
                    String subjectCode = scanner.nextLine();

                    Student student = studentRepo.getStudent(studentId);
                    Subject subject = subjectRepo.getSubject(subjectCode);

                    if (student != null && subject != null) {
                        student.enrollSubject(subject);
                    } else {
                        System.out.println("Invalid student or subject.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Student ID: ");
                    String studentId = scanner.nextLine();

                    System.out.print("Enter Subject Code: ");
                    String subjectCode = scanner.nextLine();

                    Student student = studentRepo.getStudent(studentId);
                    Subject subject = subjectRepo.getSubject(subjectCode);

                    if (student != null && subject != null) {
                        student.dropSubject(subject);
                    } else {
                        System.out.println("Invalid student or subject.");
                    }
                }
                case 4 -> studentRepo.displayAllStudents();
                case 5 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
