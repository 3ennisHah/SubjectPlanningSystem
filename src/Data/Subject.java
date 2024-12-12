package Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Subject {
    private String subjectCode;
    private String subjectName;
    private int creditHours;
    private String[] prerequisites; // Prerequisite subject codes
    private boolean isCore; // Indicates if the subject is core or elective
    private int subjectYear; // Indicates the year the subject is typically taken (1, 2, or 3)
    private boolean internationalOnly; // Indicates if the subject is for international students only
    private boolean completed; // Indicates if the subject has been completed by the student

    private static final Map<String, Subject> SUBJECT_REGISTRY = new HashMap<>();

    // Constructor
    public Subject(String subjectCode, String subjectName, int creditHours, String[] prerequisites, boolean isCore, int subjectYear, boolean internationalOnly) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.creditHours = creditHours;
        this.prerequisites = prerequisites;
        this.isCore = isCore;
        this.subjectYear = subjectYear;
        this.internationalOnly = internationalOnly;
        this.completed = false; // Default to not completed
    }

    public static Subject valueOf(String subjectCode) {
        return SUBJECT_REGISTRY.get(subjectCode);
    }

    // Getters
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

    public boolean isCore() {
        return isCore;
    }

    public int getSubjectYear() {
        return subjectYear;
    }

    public boolean isInternationalOnly() {
        return internationalOnly;
    }

    public boolean isCompleted() {
        return completed;
    }

    // Setter for completed
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // New method to check if the subject belongs to Year 1
    public boolean isYear1() {
        return subjectYear == 1;
    }

    // New method to check if the subject belongs to Year 2
    public boolean isYear2() {
        return subjectYear == 2;
    }

    // New method to check if the subject belongs to Year 3
    public boolean isYear3() {
        return subjectYear == 3;
    }

    public boolean hasPrerequisite() {
        return prerequisites.length > 0;
    }

    public boolean isElective() {
        return this.subjectCode.startsWith("Elective") || this.subjectCode.startsWith("FreeElective");
    }

    @Override
    public String toString() {
        return subjectName + " (" + subjectCode + ")";
    }

    // ******** Core Subjects ******** //
    public static final Subject CSC1024 = new Subject("CSC1024", "Programming Principles", 4, new String[]{}, true, 1, false);
    public static final Subject ENG1044 = new Subject("ENG1044", "English for Computer Technology Studies", 4, new String[]{}, false, 1, false);
    public static final Subject CSC1202 = new Subject("CSC1202", "Computer Organisation", 4, new String[]{}, true, 1, false);
    public static final Subject MTH1114 = new Subject("MTH1114", "Computer Mathematics", 4, new String[]{}, true, 1, false);
    public static final Subject PRG1203 = new Subject("PRG1203", "Object-Oriented Programming Fundamentals", 4, new String[]{"CSC1024"}, true, 1, false);
    public static final Subject SEG1201 = new Subject("SEG1201", "Database Fundamentals", 4, new String[]{}, true, 1, false);
    public static final Subject NET1014 = new Subject("NET1014", "Networking Principles", 4, new String[]{}, true, 1, false);
    public static final Subject CSC2104 = new Subject("CSC2104", "Operating System Fundamentals", 4, new String[]{}, true, 1, false);
    public static final Subject WEB1201 = new Subject("WEB1201", "Web Fundamentals", 4, new String[]{}, true, 1, false);
    public static final Subject MPU3193 = new Subject("MPU3193", "Falsafah dan Isu Semasa", 3, new String[]{}, false, 1, false);
    public static final Subject MPU3203 = new Subject("MPU3203", "Appreciation of Ethics and Civilisation", 3, new String[]{}, false, 1, true);
    public static final Subject MPU3183 = new Subject("MPU3183", "Penghayatan Etika dan Peradaban", 3, new String[]{}, false, 1, false);
    public static final Subject MPU3213 = new Subject("MPU3213", "Malay Language for Communication 2", 3, new String[]{}, false, 1, true);
    public static final Subject MPU3222 = new Subject("MPU3222", "Entrepreneurial Mindset and Skills", 2, new String[]{}, false, 2, false);
    public static final Subject SEG2202 = new Subject("SEG2202", "Software Engineering", 4, new String[]{"PRG1203"}, true, 2, false);
    public static final Subject MPU3412 = new Subject("MPU3412", "Community Service for Planetary Health", 2, new String[]{}, false, 2, false);
    public static final Subject CSC2103 = new Subject("CSC2103", "Data Structure & Algorithms", 4, new String[]{"CSC1024"}, true, 2, false);
    public static final Subject CSC2014 = new Subject("CSC2014", "Digital Image Processing", 4, new String[]{}, true, 2, false);
    public static final Subject PRG2104 = new Subject("PRG2104", "Object-Oriented Programming", 4, new String[]{"PRG1203"}, true, 2, false);
    public static final Subject ENG2044 = new Subject("ENG2044", "Communication Skills", 4, new String[]{}, false, 2, false);
    public static final Subject SEG3203 = new Subject("SEG3203", "Internship", 6, new String[]{"ENG2044"}, true, 3, false);
    public static final Subject CSC3206 = new Subject("CSC3206", "Artificial Intelligence", 4, new String[]{"CSC1024"}, true, 3, false);
    public static final Subject PRJ3213 = new Subject("PRJ3213", "Capstone Project 1", 3, new String[]{"ENG1044","PRG2104"}, true, 3, false);
    public static final Subject NET3204 = new Subject("NET3204", "Distributed Systems", 4, new String[]{"PRG2104"}, true, 3, false);
    public static final Subject PRJ3223 = new Subject("PRJ3223", "Capstone Project 2", 3, new String[]{"PRJ3213"}, true, 3, false);
    public static final Subject CSC3024 = new Subject("CSC3024", "Human Computer Interaction", 4, new String[]{"WEB1201"}, true, 3, false);
    public static final Subject KIAR = new Subject("KIAR", "Integrity and Anti-Corruption", 2, new String[]{}, false, 2, false);
    public static final Subject MAT1013 = new Subject("MAT1013", "Micro-credential in Computer Mathematics Fundamentals", 3, new String[]{}, true, 1, false);
    public static final Subject BIS2212 = new Subject("BIS2212", "Social & Professional Responsibilities", 2, new String[]{}, true, 2, false);

    // ******** Dummy Electives ******** //
    public static Subject FreeElective1 = new Subject("FreeElective1", "Placeholder Free Elective 1", 4, new String[]{}, false, 2, false);
    public static Subject FreeElective2 = new Subject("FreeElective2", "Placeholder Free Elective 2", 4, new String[]{}, false, 2, false);
    public static Subject FreeElective3 = new Subject("FreeElective3", "Placeholder Free Elective 3", 4, new String[]{}, false, 2, false);
    public static Subject Elective1 = new Subject("Elective1", "Placeholder Elective 1", 4, new String[]{}, true, 2, false);
    public static Subject Elective2 = new Subject("Elective2", "Placeholder Elective 2", 4, new String[]{}, true, 3, false);
    public static Subject Elective3 = new Subject("Elective3", "Placeholder Elective 3", 4, new String[]{}, true, 3, false);
    public static Subject Elective4 = new Subject("Elective4", "Placeholder Elective 4", 4, new String[]{}, true, 3, false);

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
