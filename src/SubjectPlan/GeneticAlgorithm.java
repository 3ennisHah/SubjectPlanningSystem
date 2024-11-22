package SubjectPlan;

import Data.*;
import Operators.FitnessFunction;

import java.util.*;

public class GeneticAlgorithm {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 200;
    private static final double MUTATION_RATE = 0.1;

    private final FitnessFunction fitnessFunction;

    public GeneticAlgorithm() {
        this.fitnessFunction = new FitnessFunction();
    }

    public Population initializePopulation(List<List<Subject>> availableSemesterPlans, Student student) {
        List<Chromosome> chromosomes = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Chromosome chromosome = new Chromosome(generateRandomPlan(availableSemesterPlans, student));
            int fitness = fitnessFunction.calculateFitness(chromosome, student, 19, 10);
            chromosome.setFitness(fitness);
            chromosomes.add(chromosome);
        }
        return new Population(chromosomes);
    }

    public Chromosome evolve(Population population, Student student, int maxCredits, int shortSemesterCredits) {
        List<List<Subject>> availableSemesterPlans =
                LineupManager.getAllSubjectsForCohort(student.getEnrollmentYear() + student.getEnrollmentIntake());

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            Population newPopulation = new Population(new ArrayList<>());

            for (int i = 0; i < POPULATION_SIZE; i++) {
                Chromosome parent1 = population.select();
                Chromosome parent2 = population.select();

                Chromosome child = crossover(parent1, parent2);
                mutate(child, availableSemesterPlans, student);

                int fitness = fitnessFunction.calculateFitness(child, student, maxCredits, shortSemesterCredits);
                child.setFitness(fitness);

                newPopulation.addChromosome(child);
            }

            population = newPopulation;

            // Early stopping if perfect chromosome is found
            if (population.getFittest().getFitness() == 100) break;
        }
        return population.getFittest();
    }

    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<List<Subject>> childSemesterPlan = new ArrayList<>(parent1.getSemesterPlan());
        for (List<Subject> semester : parent2.getSemesterPlan()) {
            if (!childSemesterPlan.contains(semester)) {
                childSemesterPlan.add(semester);
            }
        }
        return new Chromosome(childSemesterPlan);
    }

    private void mutate(Chromosome chromosome, List<List<Subject>> availableSemesterPlans, Student student) {
        Random random = new Random();
        if (random.nextDouble() < MUTATION_RATE) {
            List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();
            if (!semesterPlan.isEmpty()) {
                int semesterIndex = random.nextInt(semesterPlan.size());
                List<Subject> semester = semesterPlan.get(semesterIndex);

                if (!semester.isEmpty()) {
                    int subjectIndex = random.nextInt(semester.size());
                    List<Subject> randomSemester =
                            availableSemesterPlans.get(random.nextInt(availableSemesterPlans.size()));
                    Subject randomSubject = randomSemester.get(random.nextInt(randomSemester.size()));

                    if (!semester.contains(randomSubject) && !student.hasCompleted(randomSubject.getSubjectCode())) {
                        semester.set(subjectIndex, randomSubject);
                    }
                }
            }
        }
    }

    private List<List<Subject>> generateRandomPlan(List<List<Subject>> availableSemesterPlans, Student student) {
        List<List<Subject>> randomPlan = new ArrayList<>();
        Random random = new Random();

        for (List<Subject> semester : availableSemesterPlans) {
            List<Subject> semesterPlan = new ArrayList<>();
            int totalCredits = 0;

            for (Subject subject : semester) {
                if (!student.hasCompleted(subject.getSubjectCode()) && totalCredits + subject.getCreditHours() <= 19) {
                    semesterPlan.add(subject);
                    totalCredits += subject.getCreditHours();
                }
            }
            randomPlan.add(semesterPlan);
        }
        return randomPlan;
    }
}
