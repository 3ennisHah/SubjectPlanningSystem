package Data;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int creditHours;
    private String[] prerequisites;

    public Subject(String subjectCode, String subjectName, int creditHours, String[] prerequisites) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
    }

    // Getters and setters
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

    public boolean hasPrerequisite(String subjectCode) {
        for (String prerequisite : prerequisites) {
            if (prerequisite.equals(subjectCode)) {
                return true;
            }
        }
        return false;
    }
}

