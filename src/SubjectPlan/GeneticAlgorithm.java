package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 200;

    private final FitnessFunction fitnessFunction;
    private final CrossoverOperator crossoverOperator;
    private final MutationOperator mutationOperator;
    private final SelectionOperator selectionOperator;

    public GeneticAlgorithm() {
        this.fitnessFunction = new FitnessFunction();
        this.crossoverOperator = new CrossoverOperator();
        this.mutationOperator = new MutationOperator();
        this.selectionOperator = new SelectionOperator();
    }

    // Delegate calculateFitness to the FitnessFunction class
    public int calculateFitness(Chromosome chromosome, Student student, int maxCredits, int shortSemesterCredits) {
        return fitnessFunction.calculateFitness(chromosome, student, maxCredits, shortSemesterCredits);
    }

    public Chromosome evolve(List<List<Subject>> basePlan, Student student, int maxCredits, int shortSemesterCredits) {
        List<Chromosome> population = initializePopulation(basePlan, student);

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            List<Chromosome> newPopulation = new ArrayList<>();

            for (int i = 0; i < POPULATION_SIZE; i++) {
                Chromosome parent1 = selectionOperator.select(new Population(population));
                Chromosome parent2 = selectionOperator.select(new Population(population));

                Chromosome child = crossoverOperator.crossover(parent1, parent2);
                mutationOperator.mutate(child, basePlan, flattenSubjects(basePlan));

                child.setFitness(fitnessFunction.calculateFitness(child, student, maxCredits, shortSemesterCredits));
                newPopulation.add(child);
            }

            population = newPopulation;

            // Log generation details
            Chromosome best = population.stream().max((c1, c2) -> Integer.compare(c1.getFitness(), c2.getFitness())).orElse(null);
            System.out.println("Generation " + generation + " - Best Fitness: " + (best != null ? best.getFitness() : 0));

            // Early stopping if perfect chromosome is found
            if (best != null && best.getFitness() == 100) {
                System.out.println("Perfect fitness achieved in generation " + generation);
                return best;
            }
        }

        // Return the best chromosome after all generations
        return population.stream().max((c1, c2) -> Integer.compare(c1.getFitness(), c2.getFitness())).orElse(null);
    }

    private List<Chromosome> initializePopulation(List<List<Subject>> basePlan, Student student) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Chromosome chromosome = new Chromosome(basePlan); // Modify basePlan to shuffle subjects
            chromosome.setFitness(fitnessFunction.calculateFitness(chromosome, student, 19, 10));
            population.add(chromosome);
        }
        return population;
    }

    private List<Subject> flattenSubjects(List<List<Subject>> basePlan) {
        List<Subject> allSubjects = new ArrayList<>();
        for (List<Subject> semester : basePlan) {
            allSubjects.addAll(semester);
        }
        return allSubjects;
    }
}
