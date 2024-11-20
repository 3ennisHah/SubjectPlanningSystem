package Operators;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class GeneticAlgorithm {
    private final FitnessFunction fitnessFunction;

    public GeneticAlgorithm() {
        this.fitnessFunction = new FitnessFunction();
    }

    public Population initializePopulation(List<Chromosome> initialChromosomes) {
        return new Population(initialChromosomes);
    }

    public Chromosome evolve(Population population, List<Subject> availableSubjects, List<Subject> completedSubjects) {
        for (int generation = 0; generation < 100; generation++) { // Evolve for 100 generations
            Population newPopulation = new Population(new ArrayList<>());

            for (int i = 0; i < population.size(); i++) {
                Chromosome parent1 = population.select();
                Chromosome parent2 = population.select();

                Chromosome child = crossover(parent1, parent2);
                mutate(child, availableSubjects, completedSubjects);

                int fitness = fitnessFunction.calculateFitness(child, completedSubjects, new ArrayList<>(), 19);
                child.setFitness(fitness);

                newPopulation.addChromosome(child);
            }

            population = newPopulation;
        }

        return population.getFittest();
    }

    private Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<Subject> childSubjects = new ArrayList<>(parent1.getSubjects());
        for (Subject subject : parent2.getSubjects()) {
            if (!childSubjects.contains(subject)) {
                childSubjects.add(subject);
            }
        }
        return new Chromosome(childSubjects);
    }

    private void mutate(Chromosome chromosome, List<Subject> availableSubjects, List<Subject> completedSubjects) {
        List<Subject> subjects = chromosome.getSubjects();
        if (subjects.isEmpty()) return;

        int indexToReplace = (int) (Math.random() * subjects.size());
        Subject randomSubject = availableSubjects.get((int) (Math.random() * availableSubjects.size()));

        if (!completedSubjects.contains(randomSubject) && !subjects.contains(randomSubject)) {
            subjects.set(indexToReplace, randomSubject);
        }
    }
}
