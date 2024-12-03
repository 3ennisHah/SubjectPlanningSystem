package Main;

import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanner;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select a student to display their subject plan:");
        System.out.println("1. Alice Perfect (2024 January Intake)");
        System.out.println("2. Bob NoMath (2024 Math March Intake)");
        System.out.println("3. Charlie Retry (2024 August Intake)");
        System.out.print("Enter your choice (1/2/3): ");

        int choice = scanner.nextInt();

        // Dynamically determine cohortKey based on the student choice
        String cohortKey = switch (choice) {
            case 1 -> "BCS2024January";
            case 2 -> "BCS2024MathMarch";
            case 3 -> "BCS2024August";
            default -> null;
        };

        if (cohortKey == null) {
            System.out.println("Invalid choice. Exiting.");
            return;
        }

        // Initialize base lineup
        Map<String, List<Subject>> baseLineup = SubjectPlanner.initializeBaseLineup(cohortKey);

        if (baseLineup == null || baseLineup.isEmpty()) {
            System.out.println("No lineup found for cohort: " + cohortKey);
            return;
        }

        // Retrieve the selected student based on choice
        Student selectedStudent = Student.getStudentByChoice(choice, baseLineup);

        if (selectedStudent == null) {
            System.out.println("No student found for choice: " + choice);
            return;
        }

        try {
            // Plan subjects using SubjectPlanner
            SubjectPlanner subjectPlanner = new SubjectPlanner();
            System.out.println("Planning subjects for: " + selectedStudent.getName());

            List<List<Subject>> subjectPlan = subjectPlanner.planSubjects(selectedStudent);

            // Print the complete subject plan
            System.out.println("Complete Subject Plan for " + selectedStudent.getName() + ":");
            for (int i = 0; i < subjectPlan.size(); i++) {
                System.out.println("Semester " + (i + 1) + ": " + subjectPlan.get(i));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
