package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private final List<Chromosome> chromosomes;

    public Population(List<Chromosome> chromosomes) {
        this.chromosomes = new ArrayList<>(chromosomes);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public Chromosome getFittest() {
        return chromosomes.stream()
                .max((c1, c2) -> Integer.compare(c1.getFitness(), c2.getFitness()))
                .orElse(null);
    }

    public Chromosome select() {
        int totalFitness = getTotalFitness();
        int randomValue = new Random().nextInt(totalFitness);
        int cumulativeFitness = 0;

        for (Chromosome chromosome : chromosomes) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= randomValue) {
                return chromosome;
            }
        }
        return chromosomes.get(0); // Default fallback
    }

    public int size() {
        return chromosomes.size();
    }

    public int getTotalFitness() {
        return chromosomes.stream()
                .mapToInt(Chromosome::getFitness)
                .sum();
    }
}
