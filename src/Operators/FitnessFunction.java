package Operators;

import Data.Subject;
import Data.Student;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.Collections;

public class FitnessFunction {
    private static final int MAX_CREDITS_LONG_SEMESTER = 19; // Maximum credit hours for long semesters
    private static final int MAX_CREDITS_SHORT_SEMESTER = 10; // Maximum credit hours for short semesters
    private static final int MAX_FITNESS = 100; // Maximum fitness value
    private static final int PREREQUISITE_PENALTY = 15; // Penalty for unmet prerequisites
    private static final int MISSING_SUBJECT_PENALTY = 10; // Penalty for missing subjects
    private static final int OVERLOAD_SEMESTER_PENALTY = 100; // Penalty for exceeding credit hour constraints

    public int calculateFitness(List<List<Subject>> plan, Student student, List<List<Subject>> basePlan) {
        int fitness = 90;

        // Flatten all subjects from the base plan, excluding electives
        List<String> baseSubjectCodes = flattenPlan(basePlan).stream()
                .filter(code -> !code.startsWith("Elective")) // Exclude electives
                .collect(Collectors.toList());

        List<String> optimizedSubjectCodes = flattenPlan(plan);

        // Penalize for missing subjects
        for (String subjectCode : baseSubjectCodes) {
            if (!optimizedSubjectCodes.contains(subjectCode)) {
                System.out.println("[DEBUG] Missing subject: " + subjectCode + ". Penalizing " + MISSING_SUBJECT_PENALTY + " points.");
                fitness -= MISSING_SUBJECT_PENALTY;
            }
        }

        // Check and penalize for unmet prerequisites
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);

            for (Subject subject : semester) {
                if (subject.hasPrerequisite()) {
                    Subject prerequisite = subject.getPrerequisite(flattenSubjectList(plan, i));
                    if (prerequisite == null) {
                        System.out.println("[DEBUG] Unmet prerequisite for subject: " + subject.getSubjectCode() + ". Penalizing " + PREREQUISITE_PENALTY + " points.");
                        fitness -= PREREQUISITE_PENALTY;
                    }
                }
            }
        }

        // Penalize semesters that exceed credit hour limits
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            boolean isShortSemester = isShortSemester(i, student);
            int maxCredits = isShortSemester ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;

            System.out.println("[DEBUG] Semester " + (i + 1) + " credit hours: " + totalCredits + ". Max allowed: " + maxCredits);

            if (totalCredits > maxCredits) {
                System.out.println("[DEBUG] Semester " + (i + 1) + " exceeds credit hour limit. Penalizing " + OVERLOAD_SEMESTER_PENALTY + " points.");
                fitness -= OVERLOAD_SEMESTER_PENALTY;
            }
        }

        // Final duplicate check for all semesters
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            List<String> subjectCodes = semester.stream().map(Subject::getSubjectCode).collect(Collectors.toList());
            Set<String> duplicates = subjectCodes.stream()
                    .filter(code -> Collections.frequency(subjectCodes, code) > 1)
                    .collect(Collectors.toSet());
            if (!duplicates.isEmpty()) {
                System.out.println("[DEBUG] Duplicate subjects found in Semester " + (i + 1) + ": " + duplicates);
            }
        }

        System.out.println("[DEBUG] Final fitness score: " + Math.max(0, Math.min(MAX_FITNESS, fitness)));
        return Math.max(0, Math.min(MAX_FITNESS, fitness));
    }

    private List<String> flattenPlan(List<List<Subject>> plan) {
        return plan.stream()
                .flatMap(List::stream)
                .map(Subject::getSubjectCode)
                .collect(Collectors.toList());
    }

    private List<Subject> flattenSubjectList(List<List<Subject>> plan, int limit) {
        return plan.subList(0, limit).stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private boolean isShortSemester(int semesterIndex, Student student) {
        // Determine the semester month based on the student's intake month
        String semesterMonth = getSemesterMonth(semesterIndex, student);

        // January is always the short semester
        boolean isShort = semesterMonth.equals("January");

        System.out.println("[DEBUG] Determining if Semester " + (semesterIndex + 1) + " is short. Month: " + semesterMonth + ", Is short: " + isShort);
        return isShort;
    }

    private String getSemesterMonth(int semesterIndex, Student student) {
        final List<String> SEMESTER_CYCLE = List.of("January", "March", "August"); // Semester months
        int offset = semesterIndex % SEMESTER_CYCLE.size();
        int startMonthIndex = SEMESTER_CYCLE.indexOf(student.getIntakeMonth());
        if (startMonthIndex == -1) {
            throw new IllegalStateException("[ERROR] Invalid intake month: " + student.getIntakeMonth());
        }
        return SEMESTER_CYCLE.get((startMonthIndex + offset) % SEMESTER_CYCLE.size());
    }
}
