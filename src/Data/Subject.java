package Data;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int creditHours;
    private String[] prerequisites; // Prerequisite subject codes
    private boolean isCore; // Indicates if the subject is core or elective
    private String name;

    // Constructor
    public Subject(String subjectCode, String name, int creditHours, String[] prerequisites, boolean isCore) {
        this.subjectCode = subjectCode;
        this.name = name;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
        this.isCore = isCore;
    }

    // Getters
    public String getSubjectCode() {
        return subjectCode;
    }

    public String getName() {
        return name;
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

    public boolean isCore() {
        return isCore;
    }

    // Check if this subject has a prerequisite
    public boolean hasPrerequisite() {
        return prerequisites.length > 0;
    }

    @Override
    public String toString() {
        return name + " (" + subjectCode + ")";
    }

    // ******** Core Subjects ******** //
    public static final Subject CSC1024 = new Subject("CSC1024", "Programming Principles", 4, new String[]{}, true);
    public static final Subject ENG1044 = new Subject("ENG1044", "English for Computer Technology Studies", 4, new String[]{}, false);
    public static final Subject CSC1202 = new Subject("CSC1202", "Computer Organisation", 4, new String[]{}, true);
    public static final Subject MTH1114 = new Subject("MTH1114", "Computer Mathematics", 4, new String[]{}, true);
    public static final Subject PRG1203 = new Subject("PRG1203", "Object-Oriented Programming Fundamentals", 4, new String[]{}, true);
    public static final Subject SEG1201 = new Subject("SEG1201", "Database Fundamentals", 4, new String[]{}, true);
    public static final Subject NET1014 = new Subject("NET1014", "Networking Principles", 4, new String[]{}, true);
    public static final Subject CSC2104 = new Subject("CSC2104", "Operating System Fundamentals", 4, new String[]{}, true);
    public static final Subject WEB1201 = new Subject("WEB1201", "Web Fundamentals", 4, new String[]{}, true);
    public static final Subject MPU3193 = new Subject("MPU3193", "Falsafah dan Isu Semasa", 3, new String[]{}, false);
    public static final Subject MPU3203 = new Subject("MPU3203", "Appreciation of Ethics and Civilisation", 3, new String[]{}, false);
    public static final Subject MPU3222 = new Subject("MPU3222", "Entrepreneurial Mindset and Skills", 2, new String[]{}, false);
    public static final Subject SEG2202 = new Subject("SEG2202", "Software Engineering", 4, new String[]{}, true);
    public static final Subject MPU3412 = new Subject("MPU3412", "Community Service for Planetary Health", 2, new String[]{}, false);
    public static final Subject CSC2103 = new Subject("CSC2103", "Data Structure & Algorithms", 4, new String[]{}, true);
    public static final Subject CSC2014 = new Subject("CSC2014", "Digital Image Processing", 4, new String[]{}, true);
    public static final Subject PRG2104 = new Subject("PRG2104", "Object-Oriented Programming", 4, new String[]{}, true);
    public static final Subject ENG2044 = new Subject("ENG2044", "Communication Skills", 4, new String[]{}, false);
    public static final Subject SEG3203 = new Subject("SEG3203", "Internship", 6, new String[]{}, true);
    public static final Subject CSC3206 = new Subject("CSC3206", "Artificial Intelligence", 4, new String[]{}, true);
    public static final Subject PRJ3213 = new Subject("PRJ3213", "Capstone Project 1", 3, new String[]{}, true);
    public static final Subject NET3204 = new Subject("NET3204", "Distributed Systems", 4, new String[]{}, true);
    public static final Subject PRJ3223 = new Subject("PRJ3223", "Capstone Project 2", 3, new String[]{}, true);
    public static final Subject CSC3024 = new Subject("CSC3024", "Human Computer Interaction", 4, new String[]{}, true);
    public static final Subject MPU3183 = new Subject("MPU3183", "Penghayatan Etika dan Peradaban", 3, new String[]{}, false);
    public static final Subject MPU3213 = new Subject("MPU3213", "Malay Language for Communication 2", 3, new String[]{}, false);
    public static final Subject KIAR = new Subject("KIAR", "Integrity and Anti-Corruption", 2, new String[]{}, false);

    // ******** Dummy Electives ******** //
    public static Subject FreeElective1 = new Subject("FreeElective1", "Placeholder Free Elective 1", 3, new String[]{}, false);
    public static Subject FreeElective2 = new Subject("FreeElective2", "Placeholder Free Elective 2", 3, new String[]{}, false);
    public static Subject FreeElective3 = new Subject("FreeElective3", "Placeholder Free Elective 3", 3, new String[]{}, false);
    public static Subject Elective1 = new Subject("Elective1", "Placeholder Elective 1", 3, new String[]{}, true);
    public static Subject Elective2 = new Subject("Elective2", "Placeholder Elective 2", 3, new String[]{}, true);
    public static Subject Elective3 = new Subject("Elective3", "Placeholder Elective 3", 3, new String[]{}, true);
    public static Subject Elective4 = new Subject("Elective4", "Placeholder Elective 4", 3, new String[]{}, true);

    // Method to Set Electives Dynamically
    public static void setElectivesForSemester(String elective, Subject subject) {
        switch (elective) {
            case "FreeElective1":
                FreeElective1 = subject;
                break;
            case "FreeElective2":
                FreeElective2 = subject;
                break;
            case "FreeElective3":
                FreeElective3 = subject;
                break;
            case "Elective1":
                Elective1 = subject;
                break;
            case "Elective2":
                Elective2 = subject;
                break;
            case "Elective3":
                Elective3 = subject;
                break;
            case "Elective4":
                Elective4 = subject;
                break;
            default:
                throw new IllegalArgumentException("Unknown elective: " + elective);
        }
    }
}
