package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    public List<List<Subject>> planSubjects(Student student) {
        String cohortKey = "BCS" + student.getEnrollmentYear() + student.getEnrollmentIntake();
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey);

        if (lineup == null) {
            throw new IllegalArgumentException("No lineup found for cohort: " + cohortKey);
        }

        List<List<Subject>> basePlan = new ArrayList<>(lineup.values());

        // Log details
        System.out.println("Planning subjects for: " + student.getName());
        System.out.println(student.getName() + " is currently in Semester " + student.getCurrentSemester());
        System.out.println("Math Requirement Satisfied: " + student.hasMathRequirement());
        System.out.println("Completed Subjects: " + student.getCompletedSubjects());

        // Check if the student is on track
        if (student.isOnTrack(lineup)) {
            System.out.println(student.getName() + " is on track. Returning base lineup.");
            return basePlan; // Directly return the base lineup
        } else {
            System.out.println(student.getName() + " is not on track. Completed subjects do not match.");
            System.out.println("Base Lineup: " + lineup);
            System.out.println("Completed Subject Codes: " + student.getCompletedSubjectCodes());
        }

        // Invoke the genetic algorithm if off-track
        Chromosome optimizedPlan = geneticAlgorithm.evolve(basePlan, student, flattenSubjects(basePlan));
        return optimizedPlan.getSemesterPlan();
    }

    private List<Subject> flattenSubjects(List<List<Subject>> basePlan) {
        List<Subject> allSubjects = new ArrayList<>();
        for (List<Subject> semester : basePlan) {
            allSubjects.addAll(semester);
        }
        return allSubjects;
    }
}
