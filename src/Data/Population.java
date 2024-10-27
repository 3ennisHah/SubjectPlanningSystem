package Data;

import java.util.ArrayList;
import java.util.List;

public class Population {
    private List<Chromosome> chromosomes;

    public Population(Student student) {
        // Initialize population based on the student's current plan
        chromosomes = new ArrayList<>();
        // Create initial population logic here...
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }

    public Chromosome getFittest() {
        // Return the chromosome with the highest fitness
        Chromosome fittest = chromosomes.get(0);
        for (Chromosome chromosome : chromosomes) {
            if (chromosome.getFitness() > fittest.getFitness()) {
                fittest = chromosome;
            }
        }
        return fittest;
    }

    public int getTotalFitness() {
        int total = 0;
        for (Chromosome chromosome : chromosomes) {
            total += chromosome.getFitness();
        }
        return total;
    }
}

