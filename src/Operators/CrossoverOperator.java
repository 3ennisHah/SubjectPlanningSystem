package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<Subject> childSubjects = new ArrayList<>();
        int crossoverPoint = (int) (Math.random() * parent1.getSubjects().size());

        // Add subjects from parent1 up to the crossover point
        childSubjects.addAll(parent1.getSubjects().subList(0, crossoverPoint));

        // Add remaining subjects from parent2
        childSubjects.addAll(parent2.getSubjects().subList(crossoverPoint, parent2.getSubjects().size()));

        return new Chromosome(childSubjects);
    }
}

