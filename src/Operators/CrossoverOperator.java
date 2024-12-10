package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CrossoverOperator {

    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome offspring = new Chromosome();
        int crossoverPoint = new Random().nextInt(parent1.getSemesterPlan().size());
        System.out.println("[DEBUG] Performing crossover at point: " + crossoverPoint);

        for (int i = 0; i < crossoverPoint; i++) {
            offspring.addSemester(new ArrayList<>(parent1.getSemesterPlan().get(i)));
        }
        for (int i = crossoverPoint; i < parent2.getSemesterPlan().size(); i++) {
            offspring.addSemester(new ArrayList<>(parent2.getSemesterPlan().get(i)));
        }

        // Ensure no duplicate subjects across all semesters
        removeGlobalDuplicates(offspring);

        return offspring;
    }

    private void removeGlobalDuplicates(Chromosome offspring) {
        Set<Subject> seenSubjects = new HashSet<>();
        for (List<Subject> semester : offspring.getSemesterPlan()) {
            semester.removeIf(subject -> !seenSubjects.add(subject));
        }
        System.out.println("[DEBUG] Removed global duplicate subjects from offspring.");
    }
}
