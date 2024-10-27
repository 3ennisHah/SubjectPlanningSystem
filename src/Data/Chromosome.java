package Data;

import java.util.List;

public class Chromosome {
    private List<Subject> subjects;
    private int fitness;

    public Chromosome(List<Subject> subjects) {
        this.subjects = subjects;
    }

    // Getters and setters
    public List<Subject> getSubjects() {
        return subjects;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return subjects.toString();
    }
}
