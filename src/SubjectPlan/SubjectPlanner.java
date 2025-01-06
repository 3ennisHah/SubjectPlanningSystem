package SubjectPlan;

import Data.*;
import Operators.*;
import Utils.PlacementHandler;
import Utils.SemesterHelper;
import Utils.SemesterValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm;

    public SubjectPlanner() {
        PlacementHandler placementHandler = new PlacementHandler();
        SemesterValidator semesterValidator = new SemesterValidator();
        FitnessFunction fitnessFunction = new FitnessFunction();
        SelectionOperator selectionOperator = new SelectionOperator();
        CrossoverOperator crossoverOperator = new CrossoverOperator();
        MutationOperator mutationOperator = new MutationOperator();

        this.geneticAlgorithm = new GeneticAlgorithm(
                placementHandler,
                semesterValidator,
                fitnessFunction,
                selectionOperator,
                crossoverOperator,
                mutationOperator
        );
    }

    public Map<String, List<Subject>> initializeBaseLineup(String cohortKey, boolean isInternational) {
        System.out.println("[DEBUG] Initializing base lineup for cohort key: " + cohortKey);
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey, isInternational);
        if (lineup == null || lineup.isEmpty()) {
            System.out.println("[ERROR] No lineup found for cohort key: " + cohortKey);
        }
        return lineup;
    }

    public List<List<Subject>> convertToPlanList(Map<String, List<Subject>> lineupMap) {
        List<List<Subject>> planList = new ArrayList<>();
        for (Map.Entry<String, List<Subject>> entry : lineupMap.entrySet()) {
            planList.add(entry.getValue());
        }
        return planList;
    }

    public Chromosome runGeneticAlgorithm(Student student, List<List<Subject>> basePlan) {
        if (basePlan == null || basePlan.isEmpty()) {
            System.out.println("[ERROR] Base plan is empty or null. Cannot run genetic algorithm.");
            return null;
        }

        // Adjust the base plan to match the student's progress
        List<List<Subject>> adjustedPlan = filterAndFixSemesters(basePlan, student);

        // Use the failing subjects from the updated Student class
        List<DatabaseSubject> failingSubjects = student.getFailingSubjects();
        List<Subject> failingSubjectList = new ArrayList<>();
        for (DatabaseSubject dbSubject : failingSubjects) {
            // Map DatabaseSubject to Subject
            failingSubjectList.add(new Subject(
                    dbSubject.getSubjectCode(),
                    dbSubject.getSubjectName(),
                    0,               // Default credit hours, update if available
                    new String[]{},  // Default prerequisites, update if available
                    true,            // Assume failing subjects are core
                    1,               // Default subject year, update if available
                    false            // Assume not international-only, update if needed
            ));
        }

        // Run the genetic algorithm using the adjusted plan and failing subjects
        return geneticAlgorithm.optimizePlan(adjustedPlan, student, failingSubjectList);
    }

    private boolean isStudentOnTrack(Student student) {
        return student.getCompletedSubjects().isEmpty() && student.getFailingSubjects().isEmpty();
    }

    public void runPlanForStudent(Student student, String cohortKey, boolean isInternational) {
        // Step 1: Initialize the base lineup for the cohort
        Map<String, List<Subject>> baseLineup = initializeBaseLineup(cohortKey, isInternational);
        if (baseLineup == null || baseLineup.isEmpty()) {
            System.out.println("[ERROR] No base plan found for cohort: " + cohortKey);
            return;
        }

        // Step 2: Convert the lineup map into a list-based plan
        List<List<Subject>> basePlan = convertToPlanList(baseLineup);

        // Step 3: Check if the student is "on track"
        if (isStudentOnTrack(student)) {
            System.out.println("[INFO] Student is on track. Displaying the base subject plan.");
            SemesterHelper.displayPlan("Base Subject Plan", basePlan);
            return; // Skip optimization for "on-track" students
        }

        // Step 4: Run the genetic algorithm for optimization
        Chromosome optimizedPlan = runGeneticAlgorithm(student, basePlan);
        if (optimizedPlan != null) {
            SemesterHelper.displayPlan("Optimized Subject Plan", optimizedPlan.getSemesterPlan());
        }
    }

    private List<List<Subject>> filterAndFixSemesters(List<List<Subject>> basePlan, Student student) {
        List<List<Subject>> adjustedPlan = new ArrayList<>();
        String currentSemester = student.getCurrentSemester();

        for (int i = 0; i < basePlan.size(); i++) {
            String semesterKey = "Semester " + (i + 1);

            // If the semester is before the student's current semester
            if (!currentSemester.equals("Unknown") && semesterKey.compareTo(currentSemester) < 0) {
                List<Subject> completedSubjects = new ArrayList<>();
                for (Subject subject : basePlan.get(i)) {
                    if (student.getCompletedSubjects().contains(subject)) {
                        completedSubjects.add(subject);
                    } else {
                        System.out.println("[DEBUG] Removing incomplete subject " + subject.getSubjectCode() + " from " + semesterKey);
                    }
                }
                adjustedPlan.add(completedSubjects);
                System.out.println("[DEBUG] Fixed " + semesterKey + ": " + completedSubjects);
            } else {
                // Add the semester as-is if it's at or after the current semester
                adjustedPlan.add(new ArrayList<>(basePlan.get(i)));
                System.out.println("[DEBUG] Keeping " + semesterKey + " as-is: " + basePlan.get(i));
            }
        }
        return adjustedPlan;
    }
}
