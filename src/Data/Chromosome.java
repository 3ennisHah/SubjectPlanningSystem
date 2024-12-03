package Data;

import java.util.List;

public class Chromosome {
    private List<List<Subject>> semesterPlan;

    public Chromosome(List<List<Subject>> semesterPlan) {
        this.semesterPlan = semesterPlan;
    }

    public List<List<Subject>> getSemesterPlan() {
        return semesterPlan;
    }

    public void setSemesterPlan(List<List<Subject>> semesterPlan) {
        this.semesterPlan = semesterPlan;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < semesterPlan.size(); i++) {
            sb.append("Semester ").append(i + 1).append(": ").append(semesterPlan.get(i)).append("\n");
        }
        return sb.toString();
    }
}
