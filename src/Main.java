//import Data.*;
//import SubjectPlan.SubjectPlanUtils;
//import SubjectPlan.SubjectPlanner;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Scanner;
//
//public class Main {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.println("Select a student to display their subject plan:");
//        System.out.println("1. Alice Perfect (2024 January Intake)");
//        System.out.println("2. May International (2024 January Intake)");
//        System.out.println("3. Bob Failing Y1 Subject (2024 Math March Intake)");
//        System.out.println("4. Mark International (2024 August Intake)");
//        System.out.print("Enter your choice (1/2/3/4): ");
//
//        int choice = scanner.nextInt();
//        Student selectedStudent = Student.getStudentByChoice(choice, null);
//
//        if (selectedStudent == null) {
//            System.out.println("Invalid choice.");
//            return;
//        }
//
//        SubjectPlanner planner = new SubjectPlanner();
//        String cohortKey = selectedStudent.constructCohortKey();
//        System.out.println("Debug: Constructed cohort key is " + cohortKey);
//
//        // Pass the student's international status to the initializeBaseLineup method
//        Map<String, List<Subject>> baseLineupMap = planner.initializeBaseLineup(cohortKey, selectedStudent.isInternational());
//        if (baseLineupMap == null || baseLineupMap.isEmpty()) {
//            System.out.println("Error: Unable to retrieve a valid base lineup for the student.");
//            return;
//        }
//
//        List<List<Subject>> basePlan = planner.convertToPlanList(baseLineupMap);
//
//        System.out.println("Base Plan for " + selectedStudent.getName() + ":");
//        SubjectPlanUtils.printPlan(basePlan);
//
//        if (!selectedStudent.isOnTrack(baseLineupMap)) {
//            System.out.println(selectedStudent.getName() + " is not on track. Invoking Genetic Algorithm...");
//
//            Chromosome bestChromosome = planner.runGeneticAlgorithm(selectedStudent, basePlan);
//
//            System.out.println("\nOptimized Subject Plan for " + selectedStudent.getName() + ":");
//            SubjectPlanUtils.printPlan(bestChromosome.getSemesterPlan());
//
//            System.out.println("\nComparison with Base Plan:");
//            SubjectPlanUtils.comparePlans(basePlan, bestChromosome.getSemesterPlan());
//        } else {
//            System.out.println(selectedStudent.getName() + " is on track.");
//        }
//    }
//}


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example subject plan
        List<List<String>> subjectPlan = Arrays.asList(
                Arrays.asList("Programming Principles", "Computer Organisation", "English for Computer Technology Studies"),
                Arrays.asList("Database Fundamentals", "Operating System Fundamentals", "Computer Mathematics"),
                Arrays.asList("Digital Image Processing", "Web Fundamentals", "Networking Principles")
        );

        // Student name and file path
        String studentName = "Alice Perfect";
        String filePath = "SubjectPlan_" + studentName.replace(" ", "_") + ".xlsx";

        // Generate the Excel file
        try {
            ExcelExporter.exportSubjectPlan(filePath, subjectPlan, studentName);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to create Excel file: " + e.getMessage());
        }
    }
}
