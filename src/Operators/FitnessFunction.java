package Operators;

import Data.Chromosome;
import Data.Subject;

public class FitnessFunction {
    public int calculateFitness(Chromosome chromosome) {
        int fitness = 0;

        for (Subject subject : chromosome.getSubjects()) {
            fitness += evaluateSubject(subject, chromosome);
        }

        // Set the calculated fitness score in the chromosome
        chromosome.setFitness(fitness);
        return fitness;
    }

    private int evaluateSubject(Subject subject, Chromosome chromosome) {
        int score = 10;  // Base score for each subject

        // Reward if prerequisites are satisfied
        for (String prerequisite : subject.getPrerequisites()) {
            if (chromosome.hasCompletedSubject(prerequisite)) {
                score += 5;
            } else {
                score -= 10;  // Penalize if prerequisite not met
            }
        }

        // Reward core subjects
        if (subject.isCoreSubject()) {
            score += 10;
        }

        // Penalize for exceeding credit limit
        int totalCredits = chromosome.getTotalCreditHours();
        if (totalCredits > chromosome.getMaxCredits()) {
            score -= (totalCredits - chromosome.getMaxCredits()) * 5;
        }

        return score;
    }
}
