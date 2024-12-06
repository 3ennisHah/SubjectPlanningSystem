package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossoverOperator {

    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        Chromosome offspring = new Chromosome();

        for (int i = 0; i < parent1.getSemesterPlan().size(); i++) {
            List<Subject> semester = new ArrayList<>();
            if (i % 2 == 0) {
                semester.addAll(parent1.getSemesterPlan().get(i));
            } else {
                semester.addAll(parent2.getSemesterPlan().get(i));
            }
            offspring.getSemesterPlan().add(semester);
        }

        return offspring;
    }


}
