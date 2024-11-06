package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2, List<Subject> completedSubjects) {
        int crossoverPoint = (int) (Math.random() * parent1.getSubjects().size());
        List<Subject> childSubjects = new ArrayList<>();

        // Add subjects from parent1 up to the crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            childSubjects.add(parent1.getSubjects().get(i));
        }

        // Add remaining subjects from parent2 beyond the crossover point
        for (int i = crossoverPoint; i < parent2.getSubjects().size(); i++) {
            Subject subject = parent2.getSubjects().get(i);
            if (!childSubjects.contains(subject) &&
                    completedSubjects.stream().noneMatch(c -> c.getSubjectCode().equals(subject.getSubjectCode()))) {
                childSubjects.add(subject);
            }
        }

        return new Chromosome(childSubjects);
    }
}

