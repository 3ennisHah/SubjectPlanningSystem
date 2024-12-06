package Operators;

import Data.Chromosome;
import Data.Subject;
import SubjectPlan.SubjectPlanUtils;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class MutationOperator {

    private final Random random = new Random();

    public Chromosome mutateChromosome(Chromosome chromosome, List<Subject> allSubjects) {
        // Example mutation logic
        int semesterIndex = random.nextInt(chromosome.getSemesterPlan().size());
        List<Subject> semester = chromosome.getSemesterPlan().get(semesterIndex);

        if (!semester.isEmpty()) {
            int subjectIndex = random.nextInt(semester.size());
            Subject subject = semester.get(subjectIndex);

            // Debug before mutation
            System.out.println("[DEBUG] Mutating subject: " + subject + " in semester " + (semesterIndex + 1));

            // Replace with a random subject
            Subject newSubject = allSubjects.get(random.nextInt(allSubjects.size()));
            Set<String> existingSubjects = SubjectPlanUtils.flattenPlan(chromosome.getSemesterPlan());

            if (!existingSubjects.contains(newSubject.getSubjectCode())) {
                semester.set(subjectIndex, newSubject);
            }

            // Debug after mutation
            System.out.println("[DEBUG] Semester after mutation: " + semester);
        }

        return chromosome;
    }
}
