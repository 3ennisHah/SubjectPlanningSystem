package Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Chromosome> chromosomes;

    public Population() {
        this.chromosomes = new ArrayList<>();
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public void addChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes.addAll(chromosomes);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public double getTotalFitness() {
        return chromosomes.stream().mapToDouble(Chromosome::getFitness).sum();
    }

    public Chromosome getFittest() {
        return chromosomes.stream()
                .max(Comparator.comparingInt(Chromosome::getFitness))
                .orElse(null);
    }

    public List<Chromosome> getTopChromosomes(int count) {
        // Return the top `count` chromosomes sorted by fitness in descending order
        return chromosomes.stream()
                .sorted(Comparator.comparingInt(Chromosome::getFitness).reversed())
                .limit(count)
                .toList();
    }

    public Chromosome selectParent() {
        // Simple roulette wheel selection for parent
        double totalFitness = getTotalFitness();
        double randomFitness = new Random().nextDouble() * totalFitness;

        double cumulativeFitness = 0.0;
        for (Chromosome chromosome : chromosomes) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= randomFitness) {
                return chromosome;
            }
        }
        return chromosomes.get(0); // Fallback in case of rounding errors
    }

    /**
     * Returns the number of chromosomes in the population.
     * @return The size of the population.
     */
    public int size() {
        return chromosomes.size();
    }
}
