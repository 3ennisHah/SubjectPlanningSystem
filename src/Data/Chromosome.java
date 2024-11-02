package Data;

import java.util.List;

public class Chromosome {
    private List<Subject> subjects;
    private int fitness;
    private final int maxCredits = 19;  // Assuming a long semester with 19 max credit hours

    public Chromosome(List<Subject> subjects) {
        this.subjects = subjects;
        this.fitness = 0;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getTotalCreditHours() {
        return subjects.stream().mapToInt(Subject::getCreditHours).sum();
    }

    public int getMaxCredits() {
        return maxCredits;
    }

    public boolean hasCompletedSubject(String subjectCode) {
        return subjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    @Override
    public String toString() {
        return subjects.toString() + " | Fitness: " + fitness;
    }
}
