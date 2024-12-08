package Operators;

import Data.Chromosome;
import Data.Student;
import Data.Subject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class GeneticAlgorithm {
    private static final int MAX_CREDITS_LONG_SEMESTER = 19; // Maximum credit hours for long semesters
    private static final int MAX_CREDITS_SHORT_SEMESTER = 10; // Maximum credit hours for short semesters
    private static final int MAX_FITNESS = 100; // Maximum fitness value

    public Chromosome optimizePlan(List<List<Subject>> basePlan, Student student, List<Subject> allSubjects) {
        List<List<Subject>> optimizedPlan = deepCopyPlan(basePlan);
        List<Subject> failedSubjects = new ArrayList<>(student.getFailedSubjects());

        System.out.println("[DEBUG] Initial Plan Validation and Failed Subject Placement");

        // Step 1: Place Failed Subjects
        for (Subject failedSubject : failedSubjects) {
            boolean placed = placeFailedSubject(failedSubject, optimizedPlan, student.getCurrentSemester());
            if (!placed) {
                System.out.println("[DEBUG] Failed to place subject " + failedSubject.getSubjectCode() + " due to credit hour constraints.");
            }
        }

        // Step 2: Validate Credit Hours
        validateAndAdjustCreditHours(optimizedPlan);

        // Step 3: Calculate Fitness Score
        int fitnessScore = calculateFitness(optimizedPlan, student, basePlan);

        // Step 4: Create Final Chromosome
        Chromosome optimizedChromosome = new Chromosome(optimizedPlan);
        optimizedChromosome.setFitness(fitnessScore);

        System.out.println("[DEBUG] Final Fitness Score: " + fitnessScore);
        return optimizedChromosome;
    }

    private boolean placeFailedSubject(Subject failedSubject, List<List<Subject>> plan, int currentSemester) {
        // Prioritize starting displacement only from Year 2 (Semester 4 onwards)
        for (int i = 3; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);

            // Skip fixed semesters (e.g., internship)
            if (isFixedSemester(semester)) {
                continue;
            }

            // Check if the subject can fit within credit hour constraints
            if (canFitInSemester(semester, failedSubject, i)) {
                semester.add(failedSubject);
                System.out.println("[DEBUG] Placed " + failedSubject.getSubjectCode() + " in semester " + (i + 1));
                return true;
            } else {
                // Try to displace a subject and place it in another semester
                boolean displaced = tryDisplacingSubject(failedSubject, plan, i);
                if (displaced) {
                    System.out.println("[DEBUG] Placed " + failedSubject.getSubjectCode() + " in semester " + (i + 1) + " after displacing another subject.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean tryDisplacingSubject(Subject subjectToPlace, List<List<Subject>> plan, int currentSemesterIndex) {
        List<Subject> currentSemester = plan.get(currentSemesterIndex);

        // Sort subjects to find the most suitable one to displace (no prerequisites, lowest credits)
        Optional<Subject> subjectToDisplace = currentSemester.stream()
                .filter(subject -> !subject.hasPrerequisite()) // Only displace subjects without prerequisites
                .min(Comparator.comparingInt(Subject::getCreditHours));

        if (subjectToDisplace.isPresent()) {
            Subject displacedSubject = subjectToDisplace.get();
            int targetSemesterIndex = findMatchingSemesterForDisplacedSubject(displacedSubject, currentSemesterIndex, plan);

            if (targetSemesterIndex == -1) {
                // Create a new semester if no suitable semester is found
                createNewSemesterAndPlace(plan, displacedSubject, currentSemesterIndex);
            } else {
                plan.get(targetSemesterIndex).add(displacedSubject);
                System.out.println("[DEBUG] Placed displaced subject " + displacedSubject.getSubjectCode() + " in semester " + (targetSemesterIndex + 1));
            }

            // Now add the new subject to the current semester
            currentSemester.remove(displacedSubject);
            currentSemester.add(subjectToPlace);
            return true;
        }

        return false;
    }

    private int findMatchingSemesterForDisplacedSubject(Subject displacedSubject, int currentSemesterIndex, List<List<Subject>> plan) {
        int semesterYear = displacedSubject.getSubjectYear();
        int correspondingMonthIndex = currentSemesterIndex % 3; // Corresponding month (March, August, January)

        // Search only for semesters that match the same year and month as the displaced subject
        for (int i = currentSemesterIndex + 1; i < plan.size(); i++) {
            if ((i % 3 == correspondingMonthIndex) && canFitInSemester(plan.get(i), displacedSubject, i)) {
                System.out.println("[DEBUG] Found matching semester " + (i + 1) + " for displaced subject " + displacedSubject.getSubjectCode());
                return i;
            }
        }

        // If no valid semester found, return -1
        return -1;
    }

    private void createNewSemesterAndPlace(List<List<Subject>> plan, Subject displacedSubject, int currentSemesterIndex) {
        // Determine if the new semester is long or short
        int newSemesterIndex = plan.size();
        boolean isShortSemester = (newSemesterIndex % 3 == 2);

        // Create a new semester
        List<Subject> newSemester = new ArrayList<>();
        plan.add(newSemester);

        // Add the displaced subject to the new semester
        newSemester.add(displacedSubject);

        System.out.println("[DEBUG] Created new semester " + (newSemesterIndex + 1) + " for displaced subject " + displacedSubject.getSubjectCode() +
                ". Type: " + (isShortSemester ? "Short" : "Long"));
    }

    private boolean canFitInSemester(List<Subject> semester, Subject subject, int semesterIndex) {
        int maxCredits = (semesterIndex % 3 == 2) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER; // Short semester every 3rd semester
        int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
        return currentCredits + subject.getCreditHours() <= maxCredits;
    }

    private boolean isFixedSemester(List<Subject> semester) {
        return semester.stream().anyMatch(s -> s.getSubjectCode().equals("SEG3203")); // Internship semester
    }

    private void validateAndAdjustCreditHours(List<List<Subject>> plan) {
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int maxCredits = (i % 3 == 2) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER; // Short semester every 3rd semester
            int currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();

            while (currentCredits > maxCredits) {
                Subject subjectToMove = semester.remove(semester.size() - 1);

                boolean moved = false;
                for (int j = i + 1; j < plan.size(); j++) {
                    if (canFitInSemester(plan.get(j), subjectToMove, j)) {
                        plan.get(j).add(subjectToMove);
                        System.out.println("[DEBUG] Moved " + subjectToMove.getSubjectCode() + " from semester " + (i + 1) + " to semester " + (j + 1));
                        moved = true;
                        break;
                    }
                }

                if (!moved) {
                    System.out.println("[DEBUG] Failed to move subject " + subjectToMove.getSubjectCode() + " due to credit hour constraints.");
                    semester.add(subjectToMove); // Put it back if no valid semester is found
                    break;
                }

                currentCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            }
        }
    }

    private int calculateFitness(List<List<Subject>> plan, Student student, List<List<Subject>> basePlan) {
        int fitness = MAX_FITNESS;

        // Flatten all subjects from base plan
        List<String> baseSubjectCodes = flattenPlan(basePlan);
        List<String> optimizedSubjectCodes = flattenPlan(plan);

        // Penalize for missing subjects
        for (String subjectCode : baseSubjectCodes) {
            if (!optimizedSubjectCodes.contains(subjectCode)) {
                fitness -= 10; // Deduct 10 points for each missing subject
            }
        }

        // Penalize semesters that exceed credit hour limits
        for (int i = 0; i < plan.size(); i++) {
            List<Subject> semester = plan.get(i);
            int totalCredits = semester.stream().mapToInt(Subject::getCreditHours).sum();
            int maxCredits = (i % 3 == 2) ? MAX_CREDITS_SHORT_SEMESTER : MAX_CREDITS_LONG_SEMESTER;

            if (totalCredits > maxCredits) {
                fitness -= 100; // Heavy penalty for exceeding credit hour constraints
            }
        }

        return Math.max(0, Math.min(MAX_FITNESS, fitness));
    }

    private List<String> flattenPlan(List<List<Subject>> plan) {
        List<String> subjectCodes = new ArrayList<>();
        for (List<Subject> semester : plan) {
            for (Subject subject : semester) {
                subjectCodes.add(subject.getSubjectCode());
            }
        }
        return subjectCodes;
    }

    private List<List<Subject>> deepCopyPlan(List<List<Subject>> original) {
        List<List<Subject>> copy = new ArrayList<>();
        for (List<Subject> semester : original) {
            copy.add(new ArrayList<>(semester));
        }
        return copy;
    }
}

