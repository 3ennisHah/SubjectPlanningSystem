package Data;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int creditHours;
    private String[] prerequisites;
    private boolean isCore;

    public Subject(String subjectCode, String subjectName, int creditHours, String[] prerequisites, boolean isCore) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
        this.isCore = isCore;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String[] getPrerequisites() {
        return prerequisites;
    }

    public boolean isCoreSubject() {
        return isCore;
    }

    @Override
    public String toString() {
        return subjectName + " (" + subjectCode + ")";
    }
}
