import Data.*;
import SubjectPlan.SubjectPlanUtils;
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
        Student selectedStudent = Student.getStudentByChoice(choice, null);

        if (selectedStudent == null) {
            System.out.println("Invalid choice.");
            return;
        }

        SubjectPlanner planner = new SubjectPlanner();
        String cohortKey = selectedStudent.constructCohortKey();
        System.out.println("Debug: Constructed cohort key is " + cohortKey);

        Map<String, List<Subject>> baseLineupMap = planner.initializeBaseLineup(cohortKey);
        if (baseLineupMap == null || baseLineupMap.isEmpty()) {
            System.out.println("Error: Unable to retrieve a valid base lineup for the student.");
            return;
        }

        List<List<Subject>> basePlan = planner.convertToPlanList(baseLineupMap);

        System.out.println("Base Plan for " + selectedStudent.getName() + ":");
        SubjectPlanUtils.printPlan(basePlan);

        if (!selectedStudent.isOnTrack(baseLineupMap)) {
            System.out.println(selectedStudent.getName() + " is not on track. Invoking Genetic Algorithm...");

            Chromosome bestChromosome = planner.runGeneticAlgorithm(selectedStudent, basePlan);

            System.out.println("\nOptimized Subject Plan for " + selectedStudent.getName() + ":");
            SubjectPlanUtils.printPlan(bestChromosome.getSemesterPlan());

            System.out.println("\nComparison with Base Plan:");
            SubjectPlanUtils.comparePlans(basePlan, bestChromosome.getSemesterPlan());
        } else {
            System.out.println(selectedStudent.getName() + " is on track.");
        }
    }
}
