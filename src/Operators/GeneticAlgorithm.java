package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class GeneticAlgorithm {
    private static final int MAX_CREDITS = 19;
    private static final double MUTATION_RATE = 0.1;
    private static final int MAX_GENERATIONS = 200;
    private final FitnessFunction fitnessFunction = new FitnessFunction();

    public Chromosome evolve(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = initializePopulation(basePlan, student, allSubjects);
        Chromosome bestChromosome = null;

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            Population nextPopulation = new Population();

            for (Chromosome chromosome : population.getChromosomes()) {
                Chromosome offspring = optimizeSubjectPlan(chromosome, basePlan, student);
                int fitness = fitnessFunction.calculateFitness(offspring, student);
                offspring.setFitness(fitness);
                nextPopulation.addChromosome(offspring);
            }

            Chromosome fittestChromosome = nextPopulation.getFittest();

            // Update the best chromosome if the new one is better
            if (bestChromosome == null || fittestChromosome.getFitness() > bestChromosome.getFitness()) {
                bestChromosome = fittestChromosome;
            }

            // Display generation and fitness score
            System.out.println("Generation " + (generation + 1) + " - Fitness score: " + fittestChromosome.getFitness());

            population = nextPopulation;
        }

        // Return the best chromosome found across all generations
        return bestChromosome;
    }

    private Population initializePopulation(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = new Population();

        for (int i = 0; i < 10; i++) {
            Chromosome chromosome = generateRandomChromosome(basePlan, student, allSubjects);
            int fitness = fitnessFunction.calculateFitness(chromosome, student);
            chromosome.setFitness(fitness);
            population.addChromosome(chromosome);
        }

        return population;
    }

    private Chromosome generateRandomChromosome(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> semesterPlan = new ArrayList<>();

        // Ensure semesterPlan is mutable
        for (List<Subject> semester : basePlan) {
            semesterPlan.add(new ArrayList<>(semester)); // Create mutable copy
        }

        List<Subject> missedSubjects = findMissedSubjects(student, basePlan);
        if (missedSubjects != null && !missedSubjects.isEmpty()) {
            distributeMissedSubjects(missedSubjects, semesterPlan);
        }

        return new Chromosome(semesterPlan);
    }

    private List<Subject> findMissedSubjects(Student student, List<List<Subject>> basePlan) {
        List<Subject> missedSubjects = new ArrayList<>();
        Set<String> completedSubjectCodes = student.getCompletedSubjectCodes();

        for (List<Subject> semester : basePlan) {
            for (Subject subject : semester) {
                if (!completedSubjectCodes.contains(subject.getSubjectCode())) {
                    missedSubjects.add(subject);
                }
            }
        }

        return missedSubjects;
    }

    private void distributeMissedSubjects(List<Subject> missedSubjects, List<List<Subject>> semesterPlan) {
        for (Subject missedSubject : missedSubjects) {
            for (List<Subject> semester : semesterPlan) {
                int semesterCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
                if (semesterCredits + missedSubject.getCreditHours() <= MAX_CREDITS) {
                    semester.add(missedSubject);
                    break;
                }
            }
        }
    }

    private Chromosome optimizeSubjectPlan(Chromosome chromosome, List<List<Subject>> basePlan, Student student) {
        Collections.shuffle(chromosome.getSemesterPlan());
        List<Subject> missedSubjects = findMissedSubjects(student, basePlan);
        distributeMissedSubjects(missedSubjects, chromosome.getSemesterPlan());
        return chromosome;
    }
}
