package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.List;
import java.util.Random;

public class MutationOperator {
    private static final double MUTATION_RATE = 0.1;

    public void mutate(Chromosome chromosome, List<List<Subject>> basePlan, List<Subject> allSubjects) {
        Random random = new Random();

        if (random.nextDouble() < MUTATION_RATE) {
            List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();

            // Randomly select a semester
            int semesterIndex = random.nextInt(semesterPlan.size());
            List<Subject> semester = semesterPlan.get(semesterIndex);

            if (!semester.isEmpty()) {
                // Randomly replace a subject in the semester
                int subjectIndex = random.nextInt(semester.size());
                Subject randomSubject = allSubjects.get(random.nextInt(allSubjects.size()));

                semester.set(subjectIndex, randomSubject);
            }
        }
    }
}
