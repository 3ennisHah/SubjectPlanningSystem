package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MutationOperator {

    public Chromosome mutateChromosome(Chromosome chromosome) {
        Random random = new Random();
        int semesterPlanSize = chromosome.getSemesterPlan().size();

        if (semesterPlanSize > 0) {
            int randomIndex = random.nextInt(semesterPlanSize);
            System.out.println("[DEBUG] Mutating semester at index: " + randomIndex);
            mutateSemester(chromosome, randomIndex);
        } else {
            System.out.println("[DEBUG] Mutation skipped: Semester plan size is zero.");
        }

        // Ensure no duplicate subjects across all semesters
        removeGlobalDuplicates(chromosome);

        return chromosome;
    }

    private void mutateSemester(Chromosome chromosome, int semesterIndex) {
        if (semesterIndex < chromosome.getSemesterPlan().size()) {
            List<Subject> newSemester = generateRandomSemester();
            chromosome.getSemesterPlan().set(semesterIndex, newSemester);
            System.out.println("[DEBUG] Mutated semester at index " + semesterIndex + " with new subjects.");
        } else {
            System.out.println("[DEBUG] Mutation skipped: Invalid semester index.");
        }
    }

    private List<Subject> generateRandomSemester() {
        // Placeholder logic to generate valid subjects for a semester
        return new ArrayList<>();
    }

    private void removeGlobalDuplicates(Chromosome chromosome) {
        List<Subject> seenSubjects = new ArrayList<>();
        for (List<Subject> semester : chromosome.getSemesterPlan()) {
            semester.removeIf(seenSubjects::contains);
            seenSubjects.addAll(semester);
        }
        System.out.println("[DEBUG] Removed global duplicate subjects after mutation.");
    }
}
