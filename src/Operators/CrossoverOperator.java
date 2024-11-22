package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        List<List<Subject>> childSemesterPlan = new ArrayList<>();
        List<List<Subject>> parent1Plan = parent1.getSemesterPlan();
        List<List<Subject>> parent2Plan = parent2.getSemesterPlan();
        Random random = new Random();

        for (int semester = 0; semester < Math.min(parent1Plan.size(), parent2Plan.size()); semester++) {
            // Randomly pick semester subjects from parent1 or parent2
            if (random.nextBoolean()) {
                childSemesterPlan.add(new ArrayList<>(parent1Plan.get(semester)));
            } else {
                childSemesterPlan.add(new ArrayList<>(parent2Plan.get(semester)));
            }
        }

        return new Chromosome(childSemesterPlan);
    }
}
