package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;

public class CrossoverOperator {
    public Chromosome crossover(Chromosome parent1, Chromosome parent2) {
        int crossoverPoint = (int) (Math.random() * parent1.getSubjects().size());
        List<Subject> childSubjects = new ArrayList<>();

        // Add subjects from parent1 up to the crossover point
        for (int i = 0; i < crossoverPoint; i++) {
            childSubjects.add(parent1.getSubjects().get(i));
        }

        // Add subjects from parent2 beyond the crossover point
        for (int i = crossoverPoint; i < parent2.getSubjects().size(); i++) {
            Subject subject = parent2.getSubjects().get(i);
            if (!childSubjects.contains(subject)) {
                childSubjects.add(subject);
            }
        }

        // Ensure prerequisites are met by removing subjects that violate the constraint
        removeInvalidSubjects(childSubjects);

        return new Chromosome(childSubjects);
    }

    private void removeInvalidSubjects(List<Subject> subjects) {
        // Example implementation to remove subjects that don't meet prerequisites
        List<Subject> validSubjects = new ArrayList<>();
        for (Subject subject : subjects) {
            boolean prerequisitesMet = true;
            for (String prerequisite : subject.getPrerequisites()) {
                if (!containsSubjectCode(validSubjects, prerequisite)) {
                    prerequisitesMet = false;
                    break;
                }
            }
            if (prerequisitesMet) {
                validSubjects.add(subject);
            }
        }
        subjects.clear();
        subjects.addAll(validSubjects);
    }

    private boolean containsSubjectCode(List<Subject> subjects, String code) {
        return subjects.stream().anyMatch(s -> s.getSubjectCode().equals(code));
    }
}

