package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Chromosome {
    private List<Subject> subjects;
    private int fitness;
    private static final int MAX_CREDITS = 19; // Maximum credits per semester

    public Chromosome(List<Subject> subjects) {
        this.subjects = new ArrayList<>(subjects);
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

    public boolean hasCompletedSubject(String subjectCode) {
        return subjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    public void filterCompletedSubjects(List<Subject> completedSubjects) {
        subjects = subjects.stream()
                .filter(subject -> completedSubjects.stream()
                        .noneMatch(completed -> completed.getSubjectCode().equals(subject.getSubjectCode())))
                .collect(Collectors.toList());
    }

    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
    }

    public boolean exceedsCreditLimit() {
        return getTotalCreditHours() > MAX_CREDITS;
    }

    public boolean containsSubject(String subjectCode) {
        return subjects.stream().anyMatch(subject -> subject.getSubjectCode().equals(subjectCode));
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "subjects=" + subjects +
                ", fitness=" + fitness +
                '}';
    }
}
