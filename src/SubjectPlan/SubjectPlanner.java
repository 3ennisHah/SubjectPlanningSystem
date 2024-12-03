package SubjectPlan;

import Data.*;
import Operators.GeneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubjectPlanner {
    public static Map<String, List<Subject>> initializeBaseLineup(String cohortKey) {
        // Use LineupManager to fetch the lineup for the given cohortKey
        return LineupManager.getLineupForCohort(cohortKey);
    }

    private final GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();

    public List<List<Subject>> planSubjects(Student student) {
        String cohortKey = "BCS" + student.getEnrollmentYear() + student.getEnrollmentIntake();
        Map<String, List<Subject>> lineup = LineupManager.getLineupForCohort(cohortKey);

        if (lineup == null || lineup.isEmpty()) {
            throw new IllegalArgumentException("No valid lineup found for cohort: " + cohortKey);
        }

        List<List<Subject>> basePlan = new ArrayList<>(lineup.values());
        System.out.println(student.getName() + " is currently in Semester " + student.getCurrentSemester());

        if (student.isOnTrack(lineup)) {
            System.out.println(student.getName() + " is on track. Returning base lineup.");
            return basePlan;
        } else {
            System.out.println(student.getName() + " is not on track. Invoking Genetic Algorithm...");
        }

        Chromosome optimizedPlan = geneticAlgorithm.evolve(basePlan, student, flattenSubjects(basePlan));
        if (optimizedPlan == null) {
            throw new IllegalStateException("Genetic algorithm failed to produce a valid plan.");
        }
        return optimizedPlan.getSemesterPlan();
    }

    private List<Subject> flattenSubjects(List<List<Subject>> basePlan) {
        List<Subject> allSubjects = new ArrayList<>();
        for (List<Subject> semester : basePlan) {
            if (semester != null) {
                allSubjects.addAll(semester);
            }
        }
        return allSubjects;
    }
}
