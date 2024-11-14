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

    public void planSubjects(String cohort, Student student, List<Subject> availableSubjects) {
        List<Chromosome> initialChromosomes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {  // Generate diverse initial chromosomes
            List<Subject> randomSubjects = new ArrayList<>();
            while (randomSubjects.size() < 5) {
                Subject randomSubject = availableSubjects.get((int) (Math.random() * availableSubjects.size()));
                if (!randomSubjects.contains(randomSubject) &&
                        student.getCompletedSubjects().stream().noneMatch(s -> s.getSubjectCode().equals(randomSubject.getSubjectCode()))) {
                    randomSubjects.add(randomSubject);
                }
            }
            initialChromosomes.add(new Chromosome(randomSubjects));
        }

        Population population = ga.initializePopulation(initialChromosomes);
        Chromosome bestPlan = ga.evolve(population, availableSubjects, student.getCompletedSubjects());

        System.out.println("Best plan for " + student.getName() + ":");
        System.out.println(bestPlan);
    }
}
