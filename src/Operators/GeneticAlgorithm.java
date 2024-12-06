package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;
import SubjectPlan.SubjectPlanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GeneticAlgorithm {
    private static final int MAX_GENERATIONS = 10;
    private static final double MUTATION_RATE = 0.1;
    private final FitnessFunction fitnessFunction = new FitnessFunction();
    private final SelectionOperator selectionOperator = new SelectionOperator();
    private final CrossoverOperator crossoverOperator = new CrossoverOperator();
    private final MutationOperator mutationOperator = new MutationOperator();

    public Chromosome evolve(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = initializePopulation(basePlan, student, allSubjects);
        Chromosome bestChromosome = null;

        for (int generation = 0; generation < MAX_GENERATIONS; generation++) {
            Population nextPopulation = new Population();

            System.out.println("[DEBUG] === Generation " + (generation + 1) + " ===");

            for (int i = 0; i < population.size(); i++) {
                // Selection
                Chromosome parent1 = selectionOperator.selectParent(population);
                Chromosome parent2 = selectionOperator.selectParent(population);

                // Crossover
                Chromosome offspring = crossoverOperator.crossover(parent1, parent2);

                // Fitness after crossover
                int fitnessAfterCrossover = fitnessFunction.calculateFitness(offspring, student, basePlan);
                offspring.setFitness(fitnessAfterCrossover);

                // Mutation
                if (Math.random() < MUTATION_RATE) {
                    offspring = mutationOperator.mutateChromosome(offspring, allSubjects);
                    int fitnessAfterMutation = fitnessFunction.calculateFitness(offspring, student, basePlan);
                    offspring.setFitness(fitnessAfterMutation);
                }

                nextPopulation.addChromosome(offspring);
            }

            // Log fitness scores for all chromosomes in the current generation
            System.out.println("[DEBUG] Fitness scores for generation " + (generation + 1) + ":");
            for (Chromosome chromosome : nextPopulation.getChromosomes()) {
                System.out.println("Chromosome fitness: " + chromosome.getFitness());
            }

            // Get the fittest chromosome in the current generation
            Chromosome fittestChromosome = nextPopulation.getFittest();
            System.out.println("[DEBUG] Fittest chromosome fitness: " + fittestChromosome.getFitness());

            if (bestChromosome == null || fittestChromosome.getFitness() > bestChromosome.getFitness()) {
                bestChromosome = fittestChromosome;
            }

            population = nextPopulation;
        }

        // Ensure all required subjects are included in the final plan
        bestChromosome = fixMissingSubjects(bestChromosome, basePlan);

        return bestChromosome;
    }

    private Population initializePopulation(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = new Population();

        for (int i = 0; i < 10; i++) {
            Chromosome chromosome = generateRandomChromosome(basePlan, student, allSubjects);
            int fitness = fitnessFunction.calculateFitness(chromosome, student, basePlan);
            chromosome.setFitness(fitness);
            population.addChromosome(chromosome);

            // Log initial fitness score
            System.out.println("[DEBUG] Initialized Chromosome " + i + " with fitness: " + fitness);
        }

        return population;
    }

    private Chromosome generateRandomChromosome(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> semesterPlan = new ArrayList<>();
        int currentSemester = student.getCurrentSemester();

        for (int i = 0; i < basePlan.size(); i++) {
            if (i < currentSemester - 1) {
                // Lock prior semesters
                semesterPlan.add(basePlan.get(i));
            } else {
                // Randomize semesters starting from the current semester
                semesterPlan.add(new ArrayList<>(basePlan.get(i)));
            }
        }

        List<Subject> missedSubjects = findMissedSubjects(student, basePlan);
        distributeMissedSubjects(missedSubjects, semesterPlan, currentSemester);

        return new Chromosome(semesterPlan);
    }

    private Chromosome fixMissingSubjects(Chromosome chromosome, List<List<Subject>> basePlan) {
        Set<String> baseSubjectCodes = SubjectPlanUtils.flattenPlan(basePlan);
        Set<String> chromosomeSubjectCodes = SubjectPlanUtils.flattenPlan(chromosome.getSemesterPlan());

        // Identify missing subjects
        baseSubjectCodes.removeAll(chromosomeSubjectCodes);

        // Add missing subjects to the appropriate semester
        for (String missingSubjectCode : baseSubjectCodes) {
            for (List<Subject> semester : chromosome.getSemesterPlan()) {
                int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
                if (totalCredits < 19) { // Ensure not to exceed the credit limit
                    Subject missingSubject = findSubjectByCode(missingSubjectCode, basePlan);
                    if (missingSubject != null) {
                        semester.add(missingSubject);
                        break;
                    }
                }
            }
        }

        return chromosome;
    }

    private Subject findSubjectByCode(String subjectCode, List<List<Subject>> basePlan) {
        for (List<Subject> semester : basePlan) {
            for (Subject subject : semester) {
                if (subject.getSubjectCode().equals(subjectCode)) {
                    return subject;
                }
            }
        }
        return null;
    }

    private void distributeMissedSubjects(List<Subject> missedSubjects, List<List<Subject>> semesterPlan, int currentSemester) {
        for (Subject missedSubject : missedSubjects) {
            for (int i = currentSemester - 1; i < semesterPlan.size(); i++) {
                List<Subject> semester = semesterPlan.get(i);
                int semesterCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
                if (semesterCredits + missedSubject.getCreditHours() <= 19) {
                    semester.add(missedSubject);
                    break;
                }
            }
        }
    }

    private List<Subject> findMissedSubjects(Student student, List<List<Subject>> basePlan) {
        Set<String> completedSubjectCodes = student.getCompletedSubjectCodes();
        Set<String> baseSubjectCodes = SubjectPlanUtils.flattenPlan(basePlan);

        List<Subject> missedSubjects = new ArrayList<>();
        for (List<Subject> semester : basePlan) {
            for (Subject subject : semester) {
                if (!completedSubjectCodes.contains(subject.getSubjectCode()) && !baseSubjectCodes.contains(subject.getSubjectCode())) {
                    missedSubjects.add(subject);
                }
            }
        }
        return missedSubjects;
    }
}
