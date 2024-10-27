package Operators;

import Data.Chromosome;
import Data.Population;

public class SelectionOperator {
    public Chromosome select(Population population) {
        // Basic roulette wheel selection based on fitness
        int totalFitness = population.getTotalFitness();
        int randomValue = (int) (Math.random() * totalFitness);

        int runningSum = 0;
        for (Chromosome chromosome : population.getChromosomes()) {
            runningSum += chromosome.getFitness();
            if (runningSum >= randomValue) {
                return chromosome;
            }
        }

        return population.getChromosomes().get(0); // Fallback
    }
}

