package Operators;

import Data.Chromosome;
import Data.Population;

public class SelectionOperator {

    public Chromosome select(Population population) {
        int totalFitness = population.getTotalFitness();
        int randomValue = (int) (Math.random() * totalFitness);

        int cumulativeFitness = 0;
        for (Chromosome chromosome : population.getChromosomes()) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= randomValue) {
                return chromosome;
            }
        }

        // Return the first chromosome as a fallback
        return population.getChromosomes().get(0);
    }
}
