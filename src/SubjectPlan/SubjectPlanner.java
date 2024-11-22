package SubjectPlan;

import Data.*;

import java.util.List;

public class SubjectPlanner {
    private final GeneticAlgorithm geneticAlgorithm;

    public SubjectPlanner() {
        this.geneticAlgorithm = new GeneticAlgorithm();
    }

    public List<List<Subject>> planSubjects(Student student) {
        // Get the cohort key
        String cohortKey = student.getEnrollmentYear() + student.getEnrollmentIntake();

        // Retrieve the base lineup for the cohort
        List<List<Subject>> baseLineup = LineupManager.getAllSubjectsForCohort(cohortKey);

        if (baseLineup == null || baseLineup.isEmpty()) {
            throw new IllegalArgumentException("No subject lineup found for cohort: " + cohortKey);
        }

        // Check if the student is a "perfect" student
        if (isPerfectStudent(student, baseLineup)) {
            System.out.println("Perfect fitness detected. Returning base lineup.");
            return baseLineup;
        }

        // Initialize the population using Genetic Algorithm
        Population population = geneticAlgorithm.initializePopulation(baseLineup, student);

        // Evolve to find the best chromosome
        Chromosome bestPlan = geneticAlgorithm.evolve(population, student, 19, 10);

        // Return the semester plan from the best chromosome
        return bestPlan.getSemesterPlan();
    }

    private boolean isPerfectStudent(Student student, List<List<Subject>> baseLineup) {
        for (List<Subject> semester : baseLineup) {
            for (Subject subject : semester) {
                if (!student.hasCompleted(subject.getSubjectCode())) {
                    return false;
                }
            }
        }
        return true;
    }
}
