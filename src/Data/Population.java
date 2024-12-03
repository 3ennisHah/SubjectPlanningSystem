package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Chromosome> chromosomes;

    // Default constructor
    public Population() {
        this.chromosomes = new ArrayList<>();
    }

    // Constructor with initial chromosomes
    public Population(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public double getTotalFitness() {
        return chromosomes.stream().mapToDouble(Chromosome::getFitness).sum();
    }

    public Chromosome getFittest() {
        return chromosomes.stream()
                .max((c1, c2) -> Double.compare(c1.getFitness(), c2.getFitness()))
                .orElse(null);
    }

    public Chromosome selectParent() {
        // Simple roulette wheel selection for parent
        double totalFitness = chromosomes.stream().mapToDouble(Chromosome::getFitness).sum();
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
}
