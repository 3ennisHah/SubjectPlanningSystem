package Operators;

import Data.Chromosome;
import Data.Population;

import java.util.Random;

public class SelectionOperator {
    public Chromosome select(Population population) {
        int totalFitness = population.getTotalFitness();
        if (totalFitness == 0) {
            // If all chromosomes have fitness 0, select randomly
            Random random = new Random();
            return population.getChromosomes().get(random.nextInt(population.getChromosomes().size()));
        }

        Random random = new Random();
        int selectionPoint = random.nextInt(totalFitness);
        int cumulativeFitness = 0;

        for (Chromosome chromosome : population.getChromosomes()) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= selectionPoint) {
                return chromosome;
            }
        }

        // Fallback in case no selection matches
        return population.getChromosomes().get(0);
    }
}
