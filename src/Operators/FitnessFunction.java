package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;

public class FitnessFunction {

    private static final int HARD_CONSTRAINT_PENALTY = 100;
    private static final int SOFT_CONSTRAINT_PENALTY = 20;

    public int calculateFitness(Chromosome chromosome, List<Subject> completedSubjects, List<Subject> failedSubjects, int maxCredits) {
        int fitness = 100; // Start with perfect fitness

        // Hard constraints
        if (!satisfiesPrerequisites(chromosome, completedSubjects)) {
            fitness -= HARD_CONSTRAINT_PENALTY;
        }

        if (exceedsCreditLimit(chromosome, maxCredits)) {
            fitness -= HARD_CONSTRAINT_PENALTY;
        }

        if (!subjectsOfferedThisSemester(chromosome)) {
            fitness -= HARD_CONSTRAINT_PENALTY;
        }

        if (failedPrerequisitesNotRetaken(chromosome, failedSubjects, completedSubjects)) {
            fitness -= HARD_CONSTRAINT_PENALTY;
        }

        // Soft constraints
        if (!followsOriginalPlan(chromosome)) {
            fitness -= SOFT_CONSTRAINT_PENALTY;
        }

        if (!retakesFailedSubjects(chromosome, failedSubjects)) {
            fitness -= SOFT_CONSTRAINT_PENALTY;
        }

        if (!prioritizesCoreSubjects(chromosome)) {
            fitness -= SOFT_CONSTRAINT_PENALTY;
        }

        if (!yearSpecificSubjects(chromosome)) {
            fitness -= SOFT_CONSTRAINT_PENALTY;
        }

        return Math.max(fitness, 0); // Ensure fitness is not negative
    }

    private boolean satisfiesPrerequisites(Chromosome chromosome, List<Subject> completedSubjects) {
        for (Subject subject : chromosome.getSubjects()) {
            for (String prerequisite : subject.getPrerequisites()) {
                if (completedSubjects.stream().noneMatch(s -> s.getSubjectCode().equals(prerequisite))) {
                    return false; // Prerequisite not satisfied
                }
            }
        }
        return true;
    }

    private boolean exceedsCreditLimit(Chromosome chromosome, int maxCredits) {
        return chromosome.getTotalCreditHours() > maxCredits;
    }

    private boolean subjectsOfferedThisSemester(Chromosome chromosome) {
        // Placeholder: Validate subjects offered this semester
        return true;
    }

    private boolean failedPrerequisitesNotRetaken(Chromosome chromosome, List<Subject> failedSubjects, List<Subject> completedSubjects) {
        for (Subject failed : failedSubjects) {
            if (failed.isCoreSubject() && !chromosome.getSubjects().contains(failed) && !completedSubjects.contains(failed)) {
                return true;
            }
        }
        return false;
    }

    private boolean followsOriginalPlan(Chromosome chromosome) {
        // Placeholder: Validate plan adherence
        return true;
    }

    private boolean retakesFailedSubjects(Chromosome chromosome, List<Subject> failedSubjects) {
        for (Subject failed : failedSubjects) {
            if (chromosome.getSubjects().contains(failed)) {
                return true;
            }
        }
        return false;
    }

    private boolean prioritizesCoreSubjects(Chromosome chromosome) {
        long coreCount = chromosome.getSubjects().stream().filter(Subject::isCoreSubject).count();
        return coreCount > (chromosome.getSubjects().size() / 2);
    }

    private boolean yearSpecificSubjects(Chromosome chromosome) {
        // Placeholder: Validate year-specific subjects
        return true;
    }
}
