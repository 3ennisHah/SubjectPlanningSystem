package Data;

import java.util.ArrayList;
import java.util.List;

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
        return chromosomes.stream().max((c1, c2) -> Integer.compare(c1.getFitness(), c2.getFitness())).orElse(null);
    }

    public int getTotalFitness() {
        return chromosomes.stream().mapToInt(Chromosome::getFitness).sum();
    }
}

