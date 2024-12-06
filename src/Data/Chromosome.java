package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chromosome implements Cloneable {
    private List<List<Subject>> semesterPlan;
    private int fitness;

    public Chromosome(List<List<Subject>> semesterPlan) {
        this.semesterPlan = semesterPlan;
        this.fitness = 0; // Default fitness
    }

    public Chromosome() {
        this.semesterPlan = new ArrayList<>(); // Initialize an empty semester plan
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < semesterPlan.size(); i++) {
            sb.append("Semester ").append(i + 1).append(": ").append(semesterPlan.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chromosome)) return false;
        Chromosome that = (Chromosome) o;
        return Objects.equals(semesterPlan, that.semesterPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(semesterPlan);
    }

    @Override
    public Chromosome clone() {
        List<List<Subject>> clonedPlan = new ArrayList<>();
        for (List<Subject> semester : semesterPlan) {
            clonedPlan.add(new ArrayList<>(semester));
        }
        Chromosome cloned = new Chromosome(clonedPlan);
        cloned.setFitness(this.fitness);
        return cloned;
    }
}
