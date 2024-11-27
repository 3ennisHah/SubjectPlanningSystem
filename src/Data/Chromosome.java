package Data;

import java.util.List;

public class Chromosome {
    private List<List<Subject>> semesterPlan;
    private int fitness;

    public Chromosome(List<List<Subject>> semesterPlan) {
        this.semesterPlan = semesterPlan;
        this.fitness = 0; // Default fitness
    }

    public List<List<Subject>> getSemesterPlan() {
        return semesterPlan;
    }

    public void setSemesterPlan(List<List<Subject>> semesterPlan) {
        this.semesterPlan = semesterPlan;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return "Chromosome{" +
                "semesterPlan=" + semesterPlan +
                ", fitness=" + fitness +
                '}';
    }
}
