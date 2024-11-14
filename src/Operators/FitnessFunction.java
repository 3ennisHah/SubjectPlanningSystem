package Operators;

import Data.Chromosome;
import Data.Subject;

public class FitnessFunction {
    public int calculateFitness(Chromosome chromosome) {
        int fitness = 0;

        for (Subject subject : chromosome.getSubjects()) {
            fitness += evaluateSubject(subject, chromosome);
        }

        chromosome.setFitness(fitness);
        return fitness;
    }

    private int evaluateSubject(Subject subject, Chromosome chromosome) {
        int score = 10;  // Base score

        for (String prerequisite : subject.getPrerequisites()) {
            if (chromosome.hasCompletedSubject(prerequisite)) {
                score += 5;
            } else {
                score -= 10;
            }
        }

        if (subject.isCoreSubject()) {
            score += 10;
        }

        int totalCredits = chromosome.getTotalCreditHours();
        if (totalCredits > chromosome.getMaxCredits()) {
            score -= (totalCredits - chromosome.getMaxCredits()) * 5;
        }

        return score;
    }
}
