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
        int score = 10;  // Base score for each subject

        // Check if prerequisites are satisfied
        for (String prerequisite : subject.getPrerequisites()) {
            if (!chromosome.hasCompletedSubject(prerequisite)) {
                score -= 5;  // Penalize if prerequisite not met
            }
        }

        // You can add more criteria to calculate fitness based on your constraints

        return score;
    }
}
