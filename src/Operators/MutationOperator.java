package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;

public class MutationOperator {
    public void mutate(Chromosome chromosome) {
        List<Subject> subjects = chromosome.getSubjects();
        int index1 = (int) (Math.random() * subjects.size());
        int index2 = (int) (Math.random() * subjects.size());

        // Swap two subjects, checking that it doesn’t violate prerequisites
        Subject subject1 = subjects.get(index1);
        Subject subject2 = subjects.get(index2);

        // Ensure that prerequisites are still met after the swap
        if (isPrerequisitesMet(subject1, subjects) && isPrerequisitesMet(subject2, subjects)) {
            subjects.set(index1, subject2);
            subjects.set(index2, subject1);
        }
    }

    private boolean isPrerequisitesMet(Subject subject, List<Subject> subjects) {
        for (String prerequisite : subject.getPrerequisites()) {
            boolean found = subjects.stream().anyMatch(s -> s.getSubjectCode().equals(prerequisite));
            if (!found) {
                return false;
            }
        }
        return true;
    }
}
