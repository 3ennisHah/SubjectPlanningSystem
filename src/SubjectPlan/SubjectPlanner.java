package SubjectPlan;

import Data.Chromosome;
import Data.Population;
import Data.Student;

public class SubjectPlanner {
    private Population population;
    private GeneticAlgorithm ga;

    public SubjectPlanner() {
        this.ga = new GeneticAlgorithm();
    }

    public void planSubjects(Student student) {
        // Initialize the population with the student's current plan
        this.population = ga.initializePopulation(student);

        // Evolve the population to get the best plan
        Chromosome bestPlan = ga.evolve(population);

        // Print or use the best subject plan for the student
        System.out.println("Best plan for " + student.getName() + ": " + bestPlan);
    }
}

