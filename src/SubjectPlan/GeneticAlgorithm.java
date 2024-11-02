package SubjectPlan;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Operators.CrossoverOperator;
import Operators.FitnessFunction;
import Operators.MutationOperator;
import Operators.SelectionOperator;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private SelectionOperator selectionOperator;
    private CrossoverOperator crossoverOperator;
    private MutationOperator mutationOperator;
    private FitnessFunction fitnessFunction;

    public GeneticAlgorithm() {
        this.selectionOperator = new SelectionOperator();
        this.crossoverOperator = new CrossoverOperator();
        this.mutationOperator = new MutationOperator();
        this.fitnessFunction = new FitnessFunction();
    }

    public Population initializePopulation(List<Chromosome> initialChromosomes) {
        Population population = new Population(initialChromosomes);
        for (Chromosome chromosome : population.getChromosomes()) {
            fitnessFunction.calculateFitness(chromosome);
        }
        return population;
    }

    public Chromosome evolve(Population population) {
        for (int i = 0; i < 100; i++) {  // Set to 100 generations
            List<Chromosome> newChromosomes = new ArrayList<>();

            for (int j = 0; j < population.getChromosomes().size(); j++) {
                Chromosome parent1 = selectionOperator.select(population);
                Chromosome parent2 = selectionOperator.select(population);

                Chromosome child = crossoverOperator.crossover(parent1, parent2);
                mutationOperator.mutate(child);
                fitnessFunction.calculateFitness(child);

                newChromosomes.add(child);
            }
            population = new Population(newChromosomes);
        }
        return population.getFittest();
    }
}

