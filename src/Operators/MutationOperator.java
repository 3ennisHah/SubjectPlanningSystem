package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MutationOperator {

    public void mutate(Chromosome chromosome, List<Subject> availableSubjects, List<Subject> completedSubjects) {
        List<Subject> subjects = chromosome.getSubjects();
        Set<String> currentSubjectCodes = subjects.stream()
                .map(Subject::getSubjectCode)
                .collect(Collectors.toSet());

        int index = (int) (Math.random() * subjects.size());
        Subject newSubject = availableSubjects.get((int) (Math.random() * availableSubjects.size()));

        // Replace with a new valid subject
        if (!currentSubjectCodes.contains(newSubject.getSubjectCode()) &&
                completedSubjects.stream().noneMatch(c -> c.getSubjectCode().equals(newSubject.getSubjectCode()))) {
            subjects.set(index, newSubject);
        }
    }
}
