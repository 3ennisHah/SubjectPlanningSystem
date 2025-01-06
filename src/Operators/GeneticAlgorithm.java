package Operators;

import Data.*;
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

    private static final int MAX_GENERATIONS = 5; // 5 generations
    private static final int POPULATION_SIZE = 1; // 1 population
    private static final double MUTATION_RATE = 0.1;

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

    /**
     * Handles the overall plan generation for a student.
     * - Displays the base plan if the student is "on track."
     * - Optimizes the plan otherwise.
     */
    public Chromosome optimizePlan(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        if (isStudentOnTrack(student)) {
            System.out.println("[INFO] Student is on track. Displaying the base subject plan.");
            SemesterHelper.displayPlan("Base Subject Plan", basePlan); // Display the base plan
            return null; // No optimization required
        }

        // Continue with optimization for off-track students
        System.out.println("[INFO] Student is off track. Optimizing subject plan...");
        matchDatabaseSubjects(student, basePlan);

        // Initialize the population
        Population population = initializePopulation(basePlan, student, allSubjects);

        Chromosome bestChromosome = null;
        int generation = 1;

        while (generation <= MAX_GENERATIONS) {
            evaluateFitness(population, student);

            bestChromosome = population.getFittest();
            System.out.println("Generation " + generation + " - Fitness: " + bestChromosome.getFitness());

            if (bestChromosome.getFitness() == 100) {
                System.out.println("[INFO] Optimal solution found in generation " + generation);
                break;
            }

            population = evolvePopulation(population, allSubjects, student, basePlan);
            generation++;
        }

        // Fallback if no perfect solution is found
        if (bestChromosome != null && bestChromosome.getFitness() < 100) {
            List<List<Subject>> fallbackPlan = fallbackToUtils(basePlan, student, allSubjects);
            bestChromosome.setSemesterPlan(fallbackPlan);
        }

        return bestChromosome;
    }



    private boolean isStudentOnTrack(Student student) {
        return student.getCompletedSubjects().isEmpty() && student.getFailingSubjects().isEmpty();
    }

    private void matchDatabaseSubjects(Student student, List<List<Subject>> basePlan) {
        List<Subject> completedSubjects = new ArrayList<>();

        for (List<Subject> semester : basePlan) {
            for (Subject subject : semester) {
                boolean isCompleted = student.getAllSubjects().stream()
                        .anyMatch(dbSubject -> dbSubject.getSubjectCode().equals(subject.getSubjectCode()) && !dbSubject.isFailingGrade());
                subject.setCompleted(isCompleted);
                if (isCompleted) {
                    completedSubjects.add(subject);
                }
            }
        }

        // Update student's completedSubjects list
        student.setCompletedSubjects(completedSubjects);
    }

    private Population initializePopulation(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        Population population = new Population();
        for (int i = 0; i < POPULATION_SIZE; i++) {
            List<List<Subject>> randomPlan = SemesterHelper.deepCopyPlan(basePlan);
            List<Subject> failingSubjects = mapDatabaseSubjectsToSubjects(student.getFailingSubjects());
            placementHandler.placeFailedSubjects(failingSubjects, randomPlan, student, student.getAlgorithmSemester() - 1);
            semesterValidator.validateAndAdjustCreditHours(randomPlan, student);
            Chromosome chromosome = new Chromosome(randomPlan);
            population.addChromosome(chromosome);
        }
        return population;
    }

    private void evaluateFitness(Population population, Student student) {
        for (Chromosome chromosome : population.getChromosomes()) {
            int fitness = fitnessFunction.calculateFitness(chromosome.getSemesterPlan(), student.getFailingSubjectsAsSubjects(), student);
            chromosome.setFitness(fitness);
        }
    }

    private Population evolvePopulation(Population currentPopulation, List<Subject> allSubjects, Student student, List<List<Subject>> basePlan) {
        Population nextGeneration = new Population();
        Random random = new Random();

        while (nextGeneration.size() < POPULATION_SIZE) {
            Chromosome parent1 = selectionOperator.selectParent(currentPopulation);
            Chromosome parent2 = selectionOperator.selectParent(currentPopulation);

            Chromosome offspring = crossoverOperator.crossover(parent1, parent2);

            if (random.nextDouble() < MUTATION_RATE) {
                offspring = mutationOperator.mutateChromosome(offspring);
            }

            nextGeneration.addChromosome(offspring);
        }

        evaluateFitness(nextGeneration, student);
        return nextGeneration;
    }

    private List<List<Subject>> fallbackToUtils(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> optimizedPlan = SemesterHelper.deepCopyPlan(basePlan);
        semesterValidator.validateAndAdjustCreditHours(optimizedPlan, student);

        List<Subject> failingSubjects = mapDatabaseSubjectsToSubjects(student.getFailingSubjects());
        for (Subject failingSubject : failingSubjects) {
            boolean placed = false;
            for (int i = student.getAlgorithmSemester() - 1; i < optimizedPlan.size(); i++) {
                List<Subject> semester = optimizedPlan.get(i);

                if (SemesterHelper.canFitInSemester(semester, failingSubject, i, student)) {
                    semester.add(failingSubject);
                    SemesterHelper.recalculateCreditHours(optimizedPlan, i);
                    System.out.println("[DEBUG] Placed failing subject " + failingSubject.getSubjectCode() + " in semester " + (i + 1));
                    placed = true;
                    break;
                }

                // Attempt to displace if placement fails
                if (SemesterHelper.attemptToDisplace(optimizedPlan, failingSubject, i, student)) {
                    System.out.println("[DEBUG] Placed failing subject " + failingSubject.getSubjectCode() + " in semester " + (i + 1) + " after displacing another subject.");
                    placed = true;
                    break;
                }
            }

            if (!placed) {
                // If all attempts fail, create a new semester
                System.out.println("[DEBUG] Creating a new semester for failing subject: " + failingSubject.getSubjectCode());
                List<Subject> newSemester = new ArrayList<>();
                newSemester.add(failingSubject);
                optimizedPlan.add(newSemester);
                SemesterHelper.recalculateCreditHours(optimizedPlan, optimizedPlan.size() - 1);
            }
        }
        return optimizedPlan;
    }

    private List<Subject> mapDatabaseSubjectsToSubjects(List<DatabaseSubject> dbSubjects) {
        List<Subject> subjects = new ArrayList<>();
        for (DatabaseSubject dbSubject : dbSubjects) {
            Subject subjectFromRegistry = Subject.valueOf(dbSubject.getSubjectCode());
            if (subjectFromRegistry != null) {
                Subject enrichedSubject = new Subject(
                        subjectFromRegistry.getSubjectCode(),
                        subjectFromRegistry.getSubjectName(),
                        subjectFromRegistry.getCreditHours(),
                        subjectFromRegistry.getPrerequisites(),
                        subjectFromRegistry.isCore(),
                        subjectFromRegistry.getSubjectYear(),
                        subjectFromRegistry.isInternationalOnly()
                );
                enrichedSubject.setCompleted(!dbSubject.isFailingGrade());
                subjects.add(enrichedSubject);
            } else {
                System.out.println("[WARN] Subject not found in registry: " + dbSubject.getSubjectCode());
                subjects.add(new Subject(
                        dbSubject.getSubjectCode(),
                        "Unknown Subject",
                        4,
                        new String[]{},
                        false,
                        1,
                        false
                ));
            }
        }
        return subjects;
    }
}