package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;

public class MutationOperator {
    public void mutate(Chromosome chromosome) {
        // Example mutation: Randomly swap two subjects
        List<Subject> subjects = chromosome.getSubjects();
        int index1 = (int) (Math.random() * subjects.size());
        int index2 = (int) (Math.random() * subjects.size());

        // Swap subjects
        Subject temp = subjects.get(index1);
        subjects.set(index1, subjects.get(index2));
        subjects.set(index2, temp);
    }
}

