package Data;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private final List<Chromosome> chromosomes;

    public Population(List<Chromosome> chromosomes) {
        this.chromosomes = new ArrayList<>(chromosomes);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public Chromosome getFittest() {
        return chromosomes.stream()
                .max((c1, c2) -> Integer.compare(c1.getFitness(), c2.getFitness()))
                .orElse(null);
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public int getTotalFitness() {
        return chromosomes.stream()
                .mapToInt(Chromosome::getFitness)
                .sum();
    }
}
