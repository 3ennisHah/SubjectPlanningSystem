package Data;

import java.util.ArrayList;
import java.util.List;

public class Chromosome {
    private List<List<Subject>> semesterPlan;
    private int fitness;

    public Chromosome(List<List<Subject>> semesterPlan) {
        this.semesterPlan = new ArrayList<>();
        for (List<Subject> semester : semesterPlan) {
            this.semesterPlan.add(new ArrayList<>(semester));
        }
        this.fitness = 0;
    }

    public List<List<Subject>> getSemesterPlan() {
        return semesterPlan;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int getTotalCreditHours() {
        return semesterPlan.stream()
                .flatMap(List::stream)
                .mapToInt(Subject::getCreditHours)
                .sum();
    }
}
