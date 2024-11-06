package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;

public class MutationOperator {
    public void mutate(Chromosome chromosome, List<Subject> availableSubjects, List<Subject> completedSubjects) {
        List<Subject> subjects = chromosome.getSubjects();
        int index1 = (int) (Math.random() * subjects.size());
        int index2 = (int) (Math.random() * availableSubjects.size());

        // Swap subjects only if the new subject isn't completed
        Subject newSubject = availableSubjects.get(index2);
        if (completedSubjects.stream().noneMatch(c -> c.getSubjectCode().equals(newSubject.getSubjectCode()))) {
            subjects.set(index1, newSubject);
        }
    }
}
