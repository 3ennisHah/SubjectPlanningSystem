package SubjectPlan;

import Data.Chromosome;
import Data.Population;
import Data.Subject;
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

    public Chromosome evolve(Population population, List<Subject> availableSubjects, List<Subject> completedSubjects) {
        Chromosome bestChromosome = null;

        for (int generation = 0; generation < 50; generation++) {
            List<Chromosome> newChromosomes = new ArrayList<>();

            for (int j = 0; j < population.getChromosomes().size(); j++) {
                Chromosome parent1 = selectionOperator.select(population);
                Chromosome parent2 = selectionOperator.select(population);

                Chromosome child = crossoverOperator.crossover(parent1, parent2, completedSubjects);
                mutationOperator.mutate(child, availableSubjects, completedSubjects);
                fitnessFunction.calculateFitness(child);

                newChromosomes.add(child);
            }

            population = new Population(newChromosomes);
            Chromosome currentBest = population.getFittest();
            if (bestChromosome == null || currentBest.getFitness() > bestChromosome.getFitness()) {
                bestChromosome = currentBest;
            }

            // Print current generation details
            System.out.println("Generation " + (generation + 1) + " Best Fitness: " + currentBest.getFitness());
        }

        return bestChromosome;
    }
}
