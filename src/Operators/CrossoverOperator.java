package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        // Simple single-point crossover
        int crossoverPoint = (int) (Math.random() * parent1.getSubjects().size());

        List<Subject> childSubjects = parent1.getSubjects().subList(0, crossoverPoint);
        childSubjects.addAll(parent2.getSubjects().subList(crossoverPoint, parent2.getSubjects().size()));

        return new Chromosome(childSubjects);
    }
}

