package Operators;

import Data.Chromosome;
import Data.Population;

import java.util.Random;

public class SelectionOperator {

    private final Random random = new Random();

    public Chromosome selectParent(Population population) {
        double totalFitness = population.getChromosomes().stream().mapToDouble(Chromosome::getFitness).sum();
        double rouletteWheelPosition = random.nextDouble() * totalFitness;
        double spinWheel = 0.0;
        for (Chromosome chromosome : population.getChromosomes()) {
            spinWheel += chromosome.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return chromosome;
            }
        }
        return population.getChromosomes().get(random.nextInt(population.size()));
    }
}