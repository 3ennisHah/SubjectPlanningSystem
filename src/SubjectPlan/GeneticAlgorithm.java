package SubjectPlan;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Operators.CrossoverOperator;
import Operators.FitnessFunction;
import Operators.MutationOperator;
import Operators.SelectionOperator;

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

    public Population initializePopulation(Student student) {
        // Initialize a population based on the student's current status
        return new Population(student);
    }

    public Chromosome evolve(Population population) {
        for (int i = 0; i < 100; i++) {  // Run for a fixed number of generations
            // Selection
            Chromosome parent1 = selectionOperator.select(population);
            Chromosome parent2 = selectionOperator.select(population);

            // Crossover
            Chromosome child = crossoverOperator.crossover(parent1, parent2);

            // Mutation
            mutationOperator.mutate(child);

            // Add child back into the population
            population.addChromosome(child);
        }

        // Return the best chromosome (solution) found
        return population.getFittest();
    }
}

