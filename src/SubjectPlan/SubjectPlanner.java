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
        List<Chromosome> initialChromosomes = new ArrayList<>();

        // Initialize chromosomes, filtering out completed subjects
        for (int i = 0; i < 10; i++) {  // Initialize 10 sample chromosomes
            List<Subject> randomSubjects = new ArrayList<>(availableSubjects);
            Chromosome chromosome = new Chromosome(randomSubjects);

            // Filter out any subjects that the student has already completed
            chromosome.filterCompletedSubjects(student.getCompletedSubjects());
            initialChromosomes.add(chromosome);
        }

        // Initialize population and run GA, passing availableSubjects and completedSubjects
        Population population = ga.initializePopulation(initialChromosomes);
        Chromosome bestPlan = ga.evolve(population, availableSubjects, student.getCompletedSubjects());

        System.out.println("Best plan for " + student.getName() + ": " + bestPlan);
    }
}

