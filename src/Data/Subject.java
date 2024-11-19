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
    public static final Subject NET2201 = new Subject("NET2201", "Computer Networks", 4, new String[]{}, true);
    public static final Subject MPU3183 = new Subject("MPU3183", "Penghayatan Etika dan Peradaban", 3, new String[]{}, false);
    public static final Subject MPU3213 = new Subject("MPU3213", "Malay Language for Communication 2", 3, new String[]{}, false);
    public static final Subject KIAR = new Subject("KIAR", "Integrity and Anti-Corruption", 2, new String[]{}, false);
    public static final Subject ENG2042 = new Subject("ENG2042", "Communication Skills for Professional Practice", 2, new String[]{}, false);
    public static final Subject MPU3312 = new Subject("MPU3312", "Sustainable Development in Malaysia", 2, new String[]{}, false);

    // ******** Computing Electives ******** //
    public static final Subject CSC2044 = new Subject("CSC2044", "Concurrent Programming", 4, new String[]{}, true);
    public static final Subject SEG2102 = new Subject("SEG2102", "Database Management Systems", 4, new String[]{}, true);
    public static final Subject BIS2216 = new Subject("BIS2216", "Data Mining and Knowledge Discovery Fundamentals", 4, new String[]{}, true);
    public static final Subject PRG2214 = new Subject("PRG2214", "Functional Programming Principles", 4, new String[]{}, true);
    public static final Subject NET2102 = new Subject("NET2102", "Data Communications", 4, new String[]{}, true);
    public static final Subject IST2334 = new Subject("IST2334", "Web and Network Analytics", 4, new String[]{}, true);
    public static final Subject CSC3044 = new Subject("CSC3044", "Computer Security", 4, new String[]{}, true);
    public static final Subject CSC3014 = new Subject("CSC3014", "Computer Vision", 4, new String[]{}, true);
    public static final Subject CSC3209 = new Subject("CSC3209", "Software Architecture and Design Patterns", 4, new String[]{}, true);
    public static final Subject CSC3034 = new Subject("CSC3034", "Computational Intelligence", 4, new String[]{}, true);
    public static final Subject CSC3064 = new Subject("CSC3064", "Database Engineering", 4, new String[]{}, true);
    public static final Subject PRG2205 = new Subject("PRG2205", "Programming Languages", 4, new String[]{}, true);
    public static final Subject PRG3014 = new Subject("PRG3014", "UI/UX Design and Development", 4, new String[]{}, true);


    // ******** Non-Computing (Free) Electives ******** //
    public static final Subject DECN2014 = new Subject("DECN2014", "Digital Economy", 4, new String[]{}, false);
    public static final Subject ETP2014 = new Subject("ETP2014", "StartUp Foundry", 4, new String[]{}, false);
    public static final Subject PSY2164 = new Subject("PSY2164", "Introduction to Psychology", 4, new String[]{}, false);
    public static final Subject MKT2224 = new Subject("MKT2224", "Principles of Marketing", 4, new String[]{}, false);
    public static final Subject ENT2114 = new Subject("ENT2114", "Principles of Entrepreneurship", 4, new String[]{}, false);
    public static final Subject MPU3312 = new Subject("MPU3312", "Sustainable Development in Malaysia", 2, new String[]{}, false);
    public static final Subject BIS2212 = new Subject("BIS2212", "Social & Professional Responsibilities", 2, new String[]{}, false);


    // Additional Math subject
    public static final Subject MAT1013 = new Subject("MAT1013", "Micro-Credential in Computer Mathematics Fundamentals", 3, new String[]{}, true);

    // Dummy Electives
    public static Subject FreeElective1;
    public static Subject FreeElective2;
    public static Subject FreeElective3;
    public static Subject Elective1;
    public static Subject Elective2;
    public static Subject Elective3;
    public static Subject Elective4;

    // Methods to Set Electives Dynamically
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
