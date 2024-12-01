package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;

import java.util.*;

public class GeneticAlgorithm {
    private final FitnessFunction fitnessFunction = new FitnessFunction();
    private final CrossoverOperator crossoverOperator = new CrossoverOperator();
    private final MutationOperator mutationOperator = new MutationOperator();

    public Chromosome evolve(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        // Initialize population here
        int populationSize = 50;
        Population population = initializePopulation(basePlan, populationSize, student);

        int generations = 100;

        for (int generation = 0; generation < generations; generation++) {
            Population newPopulation = new Population(new ArrayList<>());

            for (Chromosome parent1 : population.getChromosomes()) {
                Chromosome parent2 = select(population);

                Chromosome child = crossoverOperator.crossover(parent1, parent2);
                mutationOperator.mutate(child, allSubjects);
                fitnessFunction.calculateFitness(child, student);

                newPopulation.addChromosome(child);
            }

            population = newPopulation;

            Chromosome best = population.getFittest();
            if (best != null && best.getFitness() == 100) {
                return best; // Early stopping if perfect solution is found
            }
        }

        return population.getFittest(); // Return the best solution
    }

    private Population initializePopulation(List<List<Subject>> basePlan, int populationSize, Student student) {
        List<Chromosome> chromosomes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < populationSize; i++) {
            List<List<Subject>> randomPlan = new ArrayList<>();
            Set<Subject> usedSubjects = new HashSet<>();

            for (List<Subject> semester : basePlan) {
                List<Subject> randomizedSemester = new ArrayList<>();

                for (Subject subject : semester) {
                    if (!usedSubjects.contains(subject)) {
                        randomizedSemester.add(subject);
                        usedSubjects.add(subject);
                    }
                }

                // Shuffle within the semester for variety
                Collections.shuffle(randomizedSemester, random);
                randomPlan.add(randomizedSemester);
            }

            Chromosome chromosome = new Chromosome(randomPlan);
            fitnessFunction.calculateFitness(chromosome, student);
            chromosomes.add(chromosome);
        }

        return new Population(chromosomes);
    }


    private Chromosome select(Population population) {
        Random random = new Random();
        int totalFitness = population.getTotalFitness();
        int selectionPoint = random.nextInt(totalFitness);
        int cumulativeFitness = 0;

        for (Chromosome chromosome : population.getChromosomes()) {
            cumulativeFitness += chromosome.getFitness();
            if (cumulativeFitness >= selectionPoint) {
                return chromosome;
            }
        }

        return population.getChromosomes().get(0); // Fallback
    }

    private List<List<Subject>> distributeSubjects(List<List<Subject>> randomPlan) {
        List<List<Subject>> distributedPlan = new ArrayList<>();
        for (int i = 0; i < randomPlan.size(); i++) {
            distributedPlan.add(new ArrayList<>());
        }

        for (int semesterIndex = 0; semesterIndex < randomPlan.size(); semesterIndex++) {
            List<Subject> semesterSubjects = randomPlan.get(semesterIndex);
            int currentCredits = 0;

            for (Subject subject : semesterSubjects) {
                int maxCredits = isShortSemester(semesterIndex) ? 10 : 19;
                if (currentCredits + subject.getCreditHours() <= maxCredits) {
                    distributedPlan.get(semesterIndex).add(subject);
                    currentCredits += subject.getCreditHours();
                }
            }
        }

        return distributedPlan;
    }

    private boolean isShortSemester(int semesterIndex) {
        return semesterIndex == 0 || semesterIndex == 3 || semesterIndex == 6;
    }
}
