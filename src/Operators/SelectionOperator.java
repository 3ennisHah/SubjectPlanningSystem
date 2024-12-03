package Operators;

import Data.Chromosome;
import Data.Population;

import java.util.Random;

public class SelectionOperator {

    public Chromosome selectParent(Population population) {
        double totalFitness = population.getTotalFitness();
        double randomFitness = new Random().nextDouble() * totalFitness;

        double cumulativeFitness = 0.0;
        for (Chromosome chromosome : population.getChromosomes()) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= randomFitness) {
                return chromosome;
            }
        }
        return population.getChromosomes().get(0); // Fallback in case of rounding errors
    }

    public Chromosome tournamentSelection(Population population, int tournamentSize) {
        Random random = new Random();
        Population tournament = new Population();

        for (int i = 0; i < tournamentSize; i++) {
            Chromosome randomChromosome = population.getChromosomes()
                    .get(random.nextInt(population.getChromosomes().size()));
            tournament.addChromosome(randomChromosome);
        }
        return tournament.getFittest();
    }
}
