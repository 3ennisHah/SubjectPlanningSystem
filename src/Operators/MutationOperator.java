package Operators;

import Data.Chromosome;
import Data.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class MutationOperator {
    private static final double MUTATION_RATE = 0.1; // Define mutation rate as 10%

    public void mutate(Chromosome chromosome, List<Subject> allSubjects) {
        Random random = new Random();
        List<List<Subject>> semesterPlan = chromosome.getSemesterPlan();
        Set<Subject> assignedSubjects = semesterPlan.stream()
                .flatMap(List::stream)
                .collect(Collectors.toSet()); // Track all assigned subjects

        for (List<Subject> semester : semesterPlan) {
            if (!semester.isEmpty() && random.nextDouble() < MUTATION_RATE) {
                if (allSubjects.isEmpty()) {
                    System.out.println("Error: Subject pool is empty during mutation. Skipping mutation.");
                    continue; // Avoid mutation if no subjects are available
                }

                int subjectIndex = random.nextInt(semester.size());
                Subject newSubject;

                do {
                    newSubject = allSubjects.get(random.nextInt(allSubjects.size()));
                } while (assignedSubjects.contains(newSubject)); // Ensure no duplicates

                assignedSubjects.remove(semester.get(subjectIndex));
                assignedSubjects.add(newSubject);
                semester.set(subjectIndex, newSubject);
            }
        }

    }

    private List<Subject> distributeSubjectsToSemester(List<Subject> subjects, int semesterIndex) {
        List<Subject> distributedSubjects = new ArrayList<>();
        int currentCredits = 0;
        int maxCredits = semesterIndex == 0 || semesterIndex == 3 || semesterIndex == 6 ? 10 : 19;

        for (Subject subject : subjects) {
            if (currentCredits + subject.getCreditHours() <= maxCredits) {
                distributedSubjects.add(subject);
                currentCredits += subject.getCreditHours();
            }
        }

        return distributedSubjects;
    }
}
