package Operators;

import Data.Chromosome;
import Data.Population;

import java.util.Random;

public class SelectionOperator {
    private final Random random = new Random();

    public Chromosome selectParent(Population population) {
        // Tournament selection: pick the best chromosome from a random subset
        int tournamentSize = Math.min(3, population.size()); // Ensure valid tournament size
        Population tournament = new Population();

        for (int i = 0; i < tournamentSize; i++) {
            Chromosome randomChromosome = population.getChromosomes().get(random.nextInt(population.size()));
            tournament.addChromosome(randomChromosome);
        }

        Chromosome fittest = tournament.getFittest();
        System.out.println("[DEBUG] Selected fittest chromosome with fitness: " + fittest.getFitness());
        return fittest;
    }
}
