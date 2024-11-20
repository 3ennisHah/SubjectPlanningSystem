package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrossoverOperator {

    public Chromosome crossover(Chromosome parent1, Chromosome parent2, List<Subject> completedSubjects) {
        int crossoverPoint = (int) (Math.random() * parent1.getSubjects().size());
        List<Subject> childSubjects = new ArrayList<>();
        Set<String> subjectCodes = new HashSet<>();

        // Copy genes from parent1 up to crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            Subject subject = parent1.getSubjects().get(i);
            if (subjectCodes.add(subject.getSubjectCode())) {
                childSubjects.add(subject);
            }
        }

        // Copy remaining genes from parent2
        for (int i = crossoverPoint; i < parent2.getSubjects().size(); i++) {
            Subject subject = parent2.getSubjects().get(i);
            if (subjectCodes.add(subject.getSubjectCode()) &&
                    completedSubjects.stream().noneMatch(c -> c.getSubjectCode().equals(subject.getSubjectCode()))) {
                childSubjects.add(subject);
            }
        }

        return new Chromosome(childSubjects);
    }
}
