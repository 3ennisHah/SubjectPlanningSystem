package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;
import java.util.Random;

public class MutationOperator {
    public void mutate(Chromosome chromosome, List<Subject> availableSubjects) {
        Random random = new Random();
        List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();

        if (semesterPlan.isEmpty()) return;

        int semesterIndex = random.nextInt(semesterPlan.size());
        List<Subject> semester = semesterPlan.get(semesterIndex);

        if (semester.isEmpty()) return;

        int subjectIndex = random.nextInt(semester.size());
        Subject randomSubject = availableSubjects.get(random.nextInt(availableSubjects.size()));

        // Replace with a random subject if valid
        semester.set(subjectIndex, randomSubject);
    }
}
