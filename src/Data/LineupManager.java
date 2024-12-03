    package Data;

    import java.util.*;

    public class LineupManager {
        private static final Map<String, Map<String, List<Subject>>> PROGRAMME_LINEUPS = new HashMap<>();

        static {
            initializeBCS2024JanuaryLineup();
            initializeBCS2024MathMarchLineup(); // Ensure this is called
            initializeBCS2024AugustLineup();
            initializeDirectEntryY2JanuaryLineup();
        }

        private static void initializeBCS2024JanuaryLineup() {
            Map<String, List<Subject>> bcs2024January = new LinkedHashMap<>();

            bcs2024January.put("Semester1", List.of(
                    Subject.MPU3193, // Falsafah dan Isu Semasa
                    Subject.MPU3183, // Penghayatan Etika dan Peradaban
                    Subject.CSC1024  // Programming Principles
            )); // Total Credit Hours: 10

            bcs2024January.put("Semester2", List.of(
                    Subject.CSC1202, // Computer Organisation
                    Subject.MTH1114, // Computer Mathematics
                    Subject.PRG1203, // Object-Oriented Programming Fundamentals
                    Subject.ENG1044  // English for Computer Technology Studies
            )); // Total Credit Hours: 16

            bcs2024January.put("Semester3", List.of(
                    Subject.WEB1201, // Web Fundamentals
                    Subject.NET1014, // Networking Principles
                    Subject.SEG1201, // Database Fundamentals
                    Subject.CSC2104  // Operating System Fundamentals
            )); // Total Credit Hours: 16

            bcs2024January.put("Semester4", List.of(
                    Subject.MPU3222, // Entrepreneurial Mindset and Skills
                    Subject.MPU3412, // Community Service for Planetary Health
                    Subject.SEG2202, // Software Engineering
                    Subject.KIAR
            )); // Total Credit Hours: 10 (Short Semester)

            bcs2024January.put("Semester5", List.of(
                    Subject.CSC2103, // Data Structure & Algorithms
                    Subject.PRG2104, // Object-Oriented Programming
                    Subject.CSC2014, // Digital Image Processing
                    Subject.ENG2044  // Communication Skills
            )); // Total Credit Hours: 16

            bcs2024January.put("Semester6", List.of(
                    Subject.FreeElective1, // Free Elective
                    Subject.FreeElective2, // Free Elective
                    Subject.FreeElective3, // Free Elective
                    Subject.Elective1      // Elective 1
            )); // Total Credit Hours: 16

            bcs2024January.put("Semester7", List.of(
                    Subject.SEG3203 // Internship
            )); // Total Credit Hours: 6 (Short Semester)

            bcs2024January.put("Semester8", List.of(
                    Subject.CSC3206, // Artificial Intelligence
                    Subject.Elective2, // Elective 2
                    Subject.PRJ3213, // Capstone Project 1
                    Subject.Elective3 // Elective 3
            )); // Total Credit Hours: 15

            bcs2024January.put("Semester9", List.of(
                    Subject.Elective4, // Elective 4
                    Subject.NET3204, // Distributed Systems
                    Subject.PRJ3223, // Capstone Project 2
                    Subject.CSC3024  // Human Computer Interaction
            )); // Total Credit Hours: 15

            PROGRAMME_LINEUPS.put("BCS2024January", bcs2024January);
        }

        private static void initializeBCS2024MathMarchLineup() {
            Map<String, List<Subject>> bcs2024MathMarch = new LinkedHashMap<>();

            bcs2024MathMarch.put("Semester1", List.of(
                    Subject.CSC1024, // Programming Principles
                    Subject.CSC1202, // Computer Organisation
                    Subject.ENG1044,  // English for Computer Technology Studies
                    Subject.WEB1201, // Web Fundamentals
                    Subject.MAT1013  // Micro-Credential in Computer Mathematics Fundamentals
            )); // Total Credit Hours: 19 (Long Semester)

            bcs2024MathMarch.put("Semester2", List.of(
                    Subject.SEG1201, // Database Fundamentals
                    Subject.PRG1203, // Object-Oriented Programming Fundamentals
                    Subject.CSC2104, // Operating System Fundamentals
                    Subject.MTH1114  // Computer Mathematics
            )); // Total Credit Hours: 16

            bcs2024MathMarch.put("Semester3", List.of(
                    Subject.MPU3193, // Falsafah dan Isu Semasa
                    Subject.NET1014, // Networking Principles
                    Subject.MPU3183  // Penghayatan Etika dan Peradaban
            )); // Total Credit Hours: 10 (Short Semester)

            bcs2024MathMarch.put("Semester4", List.of(
                    Subject.CSC2103, // Data Structure & Algorithms
                    Subject.PRG2104, // Object-Oriented Programming
                    Subject.CSC2014, // Digital Image Processing
                    Subject.ENG2044  // Communication Skills
            )); // Total Credit Hours: 16

            bcs2024MathMarch.put("Semester5", List.of(
                    Subject.FreeElective1,  // Free Elective
                    Subject.FreeElective2,  // Free Elective
                    Subject. FreeElective3, // Free Elective
                    Subject.Elective1       // Elective 1
            )); // Total Credit Hours: 16

            bcs2024MathMarch.put("Semester6", List.of(
                    Subject.MPU3222, // Entrepreneurial Mindset and Skills
                    Subject.SEG2202, // Software Engineering
                    Subject.KIAR     // Integrity and Anti-Corruption
            )); // Total Credit Hours: 8 (Short Semester)

            bcs2024MathMarch.put("Semester7", List.of(
                    Subject.PRJ3213,   // Capstone Project 1
                    Subject.CSC3206,   // Artificial Intelligence
                    Subject.Elective2, // Elective
                    Subject.Elective3, // Elective
                    Subject.MPU3412    // Community Service for Planetary Health
            )); // Total Credit Hours: 17

            bcs2024MathMarch.put("Semester8", List.of(
                    Subject.PRJ3223,    // Capstone Project 2
                    Subject.Elective4,  // Elective
                    Subject.NET3204,    // Distributed Systems
                    Subject.CSC3024     // Human Computer Interaction
            )); // Total Credit Hours: 15

            bcs2024MathMarch.put("Semester9", List.of(
                    Subject.SEG3203  // Internship
            )); // Total Credit Hours: 6 (Short Semester)

            PROGRAMME_LINEUPS.put("BCS2024MathMarch", bcs2024MathMarch);

        }

        private static void initializeBCS2024AugustLineup() {
            Map<String, List<Subject>> bcs2024August = new LinkedHashMap<>();

            bcs2024August.put("Semester1", List.of(
                    Subject.CSC1024, // Programming Principles
                    Subject.CSC1202, // Computer Organisation
                    Subject.MTH1114  // Computer Mathematics
            )); // Total Credit Hours: 12

            bcs2024August.put("Semester2", List.of(
                    Subject.MPU3193, // Falsafah dan Isu Semasa
                    Subject.MPU3183, // Penghayatan Etika dan Peradaban
                    Subject.ENG1044  // English for Computer Technology Studies
            )); // Total Credit Hours: 10 (Short Semester)

            bcs2024August.put("Semester3", List.of(
                    Subject.NET1014, // Networking Principles
                    Subject.SEG1201, // Database Fundamentals
                    Subject.CSC2104  // Operating System Fundamentals
            )); // Total Credit Hours: 12

            bcs2024August.put("Semester4", List.of(
                    Subject.WEB1201, // Web Fundamentals
                    Subject.CSC2103, // Data Structure & Algorithms
                    Subject.PRG2104, // Object-Oriented Programming
                    Subject.CSC2014  // Digital Image Processing
            )); // Total Credit Hours: 16

            bcs2024August.put("Semester5", List.of(
                    Subject.MPU3222, // Entrepreneurial Mindset and Skills
                    Subject.SEG2202, // Software Engineering
                    Subject.MPU3412  // Community Service for Planetary Health
            )); // Total Credit Hours: 8 (Short Semester)

            bcs2024August.put("Semester6", List.of(
                    Subject.FreeElective1, // Free Elective
                    Subject.FreeElective2, // Free Elective
                    Subject.Elective1      // Elective 1
            )); // Total Credit Hours: 12

            bcs2024August.put("Semester7", List.of(
                    Subject.SEG3203 // Internship
            )); // Total Credit Hours: 6 (Short Semester)

            bcs2024August.put("Semester8", List.of(
                    Subject.CSC3206, // Artificial Intelligence
                    Subject.Elective2, // Elective 2
                    Subject.PRJ3213, // Capstone Project 1
                    Subject.Elective3 // Elective 3
            )); // Total Credit Hours: 15

            PROGRAMME_LINEUPS.put("BCS2024August", bcs2024August);
        }

        // Initialize BCS (Math) January 2024 Intake
        private static void initializeBCS2024MathJanuaryLineup() {
            Map<String, List<Subject>> bcs2024MathJanuary = new LinkedHashMap<>();

            bcs2024MathJanuary.put("Semester1", List.of(
                    Subject.MPU3193, // Falsafah dan Isu Semasa
                    Subject.MPU3183, // Penghayatan Etika dan Peradaban
                    Subject.CSC1024, // Programming Principles
                    Subject.MAT1013  // Micro-credential in Computer Mathematics Fundamentals
            )); // Total Credit Hours: 13

            bcs2024MathJanuary.put("Semester2", List.of(
                    Subject.ENG1044, // English for Computer Technology Studies
                    Subject.CSC1202, // Computer Organisation
                    Subject.MTH1114, // Computer Mathematics
                    Subject.PRG1203  // Object-Oriented Programming Fundamentals
            )); // Total Credit Hours: 16

            bcs2024MathJanuary.put("Semester3", List.of(
                    Subject.SEG1201, // Database Fundamentals
                    Subject.NET1014, // Networking Principles
                    Subject.CSC2104, // Operating System Fundamentals
                    Subject.WEB1201  // Web Fundamentals
            )); // Total Credit Hours: 16

            PROGRAMME_LINEUPS.put("BCS2024MathJanuary", bcs2024MathJanuary);
        }

        // Initialize Direct Entry Y2 January 2024 Intake
        private static void initializeDirectEntryY2JanuaryLineup() {
            Map<String, List<Subject>> directEntryY2January = new LinkedHashMap<>();

            directEntryY2January.put("Semester4", List.of(
                    Subject.BIS2212, // Social & Professional Responsibilities
                    Subject.MPU3193, // Falsafah dan Isu Semasa
                    Subject.MPU3183, // Penghayatan Etika dan Peradaban
                    Subject.CSC2103, // Data Structure & Algorithms
                    Subject.PRG2104, // Object-Oriented Programming
                    Subject.ENG2044, // Communication Skills
                    Subject.CSC2014  // Digital Image Processing
            )); // Total Credit Hours: 22

            directEntryY2January.put("Semester5", List.of(
                    Subject.MPU3222, // Entrepreneurial Mindset and Skills
                    Subject.MPU3412, // Community Service for Planetary Health
                    Subject.FreeElective1, // Free Elective
                    Subject.FreeElective2, // Free Elective
                    Subject.SEG2202, // Software Engineering
                    Subject.Elective1  // Elective 1
            )); // Total Credit Hours: 16

            PROGRAMME_LINEUPS.put("DirectEntryY2January", directEntryY2January);
        }

        public static Map<String, List<Subject>> getLineupForCohort(String cohort) {
            return PROGRAMME_LINEUPS.getOrDefault(cohort, null);
        }
    }
