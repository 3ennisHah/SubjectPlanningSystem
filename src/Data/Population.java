package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Population {
    private List<Chromosome> chromosomes;

    public Population(List<Chromosome> initialChromosomes) {
        this.chromosomes = new ArrayList<>(initialChromosomes);
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public Chromosome getFittest() {
        return chromosomes.stream().max(Comparator.comparingInt(Chromosome::getFitness)).orElse(null);
    }

    public Chromosome getLeastFit() {
        return chromosomes.stream().min(Comparator.comparingInt(Chromosome::getFitness)).orElse(null);
    }

    public int getTotalFitness() {
        return chromosomes.stream().mapToInt(Chromosome::getFitness).sum();
    }
}

