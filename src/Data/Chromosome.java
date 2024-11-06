package Data;

import java.util.List;
import java.util.stream.Collectors;

public class Chromosome {
    private List<Subject> subjects;
    private int fitness;
    private static final int MAX_CREDITS = 19;  // Set maximum credit hours per semester

    public Chromosome(List<Subject> subjects) {
        this.subjects = subjects;
        calculateFitness();
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {  // Add this method to allow setting fitness externally
        this.fitness = fitness;
    }

    public int getMaxCredits() {  // Add this method to access MAX_CREDITS
        return MAX_CREDITS;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
        calculateFitness();  // Recalculate fitness if subjects are changed
    }

    public int getTotalCreditHours() {
        return subjects.stream().mapToInt(Subject::getCreditHours).sum();
    }

    public boolean hasCompletedSubject(String subjectCode) {
        return subjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    public void filterCompletedSubjects(List<Subject> completedSubjects) {
        subjects = subjects.stream()
                .filter(subject -> completedSubjects.stream()
                        .noneMatch(completed -> completed.getSubjectCode().equals(subject.getSubjectCode())))
                .collect(Collectors.toList());
    }

    private void calculateFitness() {
        fitness = 0;
        for (Subject subject : subjects) {
            fitness += subject.getCreditHours();
            // Add extra points for core subjects or satisfy specific constraints
            if (subject.isCoreSubject()) fitness += 5;
        }
        if (getTotalCreditHours() > MAX_CREDITS) {
            fitness -= (getTotalCreditHours() - MAX_CREDITS) * 10;  // Penalize for excess credit hours
        }
    }

    @Override
    public String toString() {
        return subjects.toString() + " | Fitness: " + fitness;
    }
}