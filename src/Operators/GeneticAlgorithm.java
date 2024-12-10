package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;
import Utils.PlacementHandler;
import Utils.SemesterHelper;
import Utils.SemesterValidator;

import java.util.*;

public class GeneticAlgorithm {
    private final PlacementHandler placementHandler;
    private final SemesterValidator semesterValidator;
    private final FitnessFunction fitnessFunction;
    private final SelectionOperator selectionOperator;
    private final CrossoverOperator crossoverOperator;
    private final MutationOperator mutationOperator;

    private static final int MAX_GENERATIONS = 10; // Max number of generations to simulate
    private static final int POPULATION_SIZE = 1; // Number of chromosomes per generation
    private static final double MUTATION_RATE = 0.1; // Chance of mutation per chromosome

    public GeneticAlgorithm(
            PlacementHandler placementHandler,
            SemesterValidator semesterValidator,
            FitnessFunction fitnessFunction,
            SelectionOperator selectionOperator,
            CrossoverOperator crossoverOperator,
            MutationOperator mutationOperator
    ) {
        this.placementHandler = placementHandler;
        this.semesterValidator = semesterValidator;
        this.fitnessFunction = fitnessFunction;
        this.selectionOperator = selectionOperator;
        this.crossoverOperator = crossoverOperator;
        this.mutationOperator = mutationOperator;
    }

    public Chromosome optimizePlan(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = initializePopulation(basePlan, student, allSubjects);

        int generation = 1;
        Chromosome bestChromosome = null;

        while (generation <= MAX_GENERATIONS) {
            // Evaluate fitness of the population
            evaluateFitness(population, student, basePlan);

            // Log the fitness of the best chromosome in the current generation
            bestChromosome = population.getFittest();
            System.out.println("Generation " + generation + " - Fitness score: " + bestChromosome.getFitness());

            // If a perfect solution is found, exit early
            if (bestChromosome.getFitness() == 100) {
                System.out.println("[INFO] Optimal solution found in generation " + generation);
                break;
            }

            // Evolve the population for the next generation
            population = evolvePopulation(population, allSubjects, student, basePlan);

            generation++;
        }

        // Ensure fallback to the `Utils` package for final adjustments
        if (bestChromosome != null && bestChromosome.getFitness() < 100) {
            System.out.println("[INFO] Falling back to Utils package for final adjustments.");
            bestChromosome.setSemesterPlan(fallbackToUtils(basePlan, student, allSubjects));
        }

        return bestChromosome;
    }

    private Population initializePopulation(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = new Population();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            // Create random variations of the base plan
            List<List<Subject>> randomPlan = SemesterHelper.deepCopyPlan(basePlan);
            placementHandler.placeFailedSubjects(student.getFailedSubjects(), randomPlan, student);
            Chromosome chromosome = new Chromosome(randomPlan);
            population.addChromosome(chromosome);
        }
        return population;
    }

    private void evaluateFitness(Population population, Student student, List<List<Subject>> basePlan) {
        for (Chromosome chromosome : population.getChromosomes()) {
            int fitness = fitnessFunction.calculateFitness(chromosome.getSemesterPlan(), student, basePlan);
            chromosome.setFitness(fitness);
        }
    }

    private Population evolvePopulation(Population currentPopulation, List<Subject> allSubjects, Student student, List<List<Subject>> basePlan) {
        Population nextGeneration = new Population();
        Random random = new Random();

        // Perform crossover and mutation to generate the next generation
        while (nextGeneration.size() < POPULATION_SIZE) {
            Chromosome parent1 = selectionOperator.selectParent(currentPopulation);
            Chromosome parent2 = selectionOperator.selectParent(currentPopulation);

            // Apply crossover to create offspring
            Chromosome offspring = crossoverOperator.crossover(parent1, parent2);

            // Apply mutation with a given probability
            if (random.nextDouble() < MUTATION_RATE) {
                offspring = mutationOperator.mutateChromosome(offspring);
            }

            // Add offspring to the next generation
            nextGeneration.addChromosome(offspring);
        }

        // Calculate fitness for the new population
        evaluateFitness(nextGeneration, student, basePlan);

        return nextGeneration;
    }

    private List<List<Subject>> fallbackToUtils(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> optimizedPlan = SemesterHelper.deepCopyPlan(basePlan);

        // Ensure there are no duplicate subjects in any semester
        for (int semesterIndex = 0; semesterIndex < optimizedPlan.size(); semesterIndex++) {
            List<Subject> semesterSubjects = optimizedPlan.get(semesterIndex);
            Set<String> seenSubjects = new HashSet<>();
            List<Subject> cleanedSubjects = new ArrayList<>();

            for (Subject subject : semesterSubjects) {
                if (!seenSubjects.contains(subject.getSubjectCode())) {
                    cleanedSubjects.add(subject); // Add only if not already in the list
                    seenSubjects.add(subject.getSubjectCode());
                } else {
                    System.out.println("[DEBUG] Removing duplicate subject: " + subject.getSubjectCode() + " from Semester " + (semesterIndex + 1));
                }
            }
            optimizedPlan.set(semesterIndex, cleanedSubjects);
        }

        placementHandler.placeFailedSubjects(student.getFailedSubjects(), optimizedPlan, student);
        semesterValidator.validateAndAdjustCreditHours(optimizedPlan, student);

        return optimizedPlan;
    }

}
