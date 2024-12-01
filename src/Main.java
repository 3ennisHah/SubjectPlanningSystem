package Main;

import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanner;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SubjectPlanner subjectPlanner = new SubjectPlanner();

        System.out.println("Select a student to display their subject plan:");
        System.out.println("1. Alice Perfect (2024 January Intake)");
        System.out.println("2. Bob NoMath (2024 Math March Intake)");
        System.out.println("3. Charlie Retry (2024 August Intake)");
        System.out.print("Enter your choice (1/2/3): ");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        Student selectedStudent = Student.getStudentByChoice(choice, null); // Provide base lineup if necessary

        try {
            List<List<Subject>> subjectPlan = subjectPlanner.planSubjects(selectedStudent);

            System.out.println("Complete Subject Plan for " + selectedStudent.getName() + ":");
            for (int i = 0; i < subjectPlan.size(); i++) {
                System.out.println("Semester " + (i + 1) + ": " + subjectPlan.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
