package SubjectPlan;

import Data.Chromosome;
import Data.Population;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectPlanner {
    private GeneticAlgorithm ga;

    public SubjectPlanner() {
        this.ga = new GeneticAlgorithm();
    }

    public void planSubjects(Student student, List<Subject> availableSubjects) {
        // Create initial chromosomes for population based on student data
        List<Chromosome> initialChromosomes = new ArrayList<>();

        // Initialize chromosomes with random selection of subjects, respecting constraints
        for (int i = 0; i < 10; i++) {  // Initialize 10 sample chromosomes
            List<Subject> randomSubjects = new ArrayList<>(availableSubjects);
            Chromosome chromosome = new Chromosome(randomSubjects);
            initialChromosomes.add(chromosome);
        }

        // Initialize population and run GA
        Population population = ga.initializePopulation(initialChromosomes);
        Chromosome bestPlan = ga.evolve(population);

        System.out.println("Best plan for " + student.getName() + ": " + bestPlan);
    }
}

