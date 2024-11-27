package Data;

import java.util.*;

public class LineupManager {
    private static final Map<String, Map<String, List<Subject>>> PROGRAMME_LINEUPS = new HashMap<>();

    static {
        initializeBCS2024Lineups();
        initializeBCS2024MathMarchLineup(); // Add the missing lineup for MathMarch intake
    }

    private static void initializeBCS2024Lineups() {
        Map<String, List<Subject>> bcs2024 = new HashMap<>();

        bcs2024.put("Semester1", List.of(Subject.CSC1024, Subject.ENG1044));
        bcs2024.put("Semester2", List.of(Subject.MTH1114, Subject.PRG1203));
        bcs2024.put("Semester3", List.of(Subject.SEG1201, Subject.NET1014));
        bcs2024.put("Semester4", List.of(Subject.CSC2104, Subject.WEB1201));
        bcs2024.put("Semester5", List.of(Subject.MPU3193, Subject.MPU3203));
        bcs2024.put("Semester6", List.of(Subject.MPU3222, Subject.KIAR));
        bcs2024.put("Semester7", List.of(Subject.SEG3203));
        bcs2024.put("Semester8", List.of(Subject.CSC3206, Subject.PRJ3213));
        bcs2024.put("Semester9", List.of(Subject.NET3204, Subject.PRJ3223));

        PROGRAMME_LINEUPS.put("BCS2024January", bcs2024);
    }

    private static void initializeBCS2024MathMarchLineup() {
        Map<String, List<Subject>> bcs2024MathMarch = new HashMap<>();

        bcs2024MathMarch.put("Semester1", List.of(
                Subject.ENG1044, Subject.CSC1202, Subject.CSC1024, Subject.WEB1201
        ));
        bcs2024MathMarch.put("Semester2", List.of(
                Subject.SEG1201, Subject.PRG1203, Subject.CSC2104, Subject.MTH1114
        ));
        bcs2024MathMarch.put("Semester3", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.MPU3183, Subject.MPU3213
        ));
        bcs2024MathMarch.put("Semester4", List.of(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044
        ));
        bcs2024MathMarch.put("Semester5", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs2024MathMarch.put("Semester6", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        bcs2024MathMarch.put("Semester7", List.of(
                Subject.SEG3203
        ));
        bcs2024MathMarch.put("Semester8", List.of(
                Subject.CSC3206, Subject.PRJ3213, Subject.Elective2, Subject.Elective3
        ));
        bcs2024MathMarch.put("Semester9", List.of(
                Subject.NET3204, Subject.PRJ3223, Subject.CSC3024, Subject.Elective4
        ));

        PROGRAMME_LINEUPS.put("BCS2024MathMarch", bcs2024MathMarch);
    }


    public static Map<String, List<Subject>> getLineupForCohort(String cohort) {
        System.out.println("Available cohorts: " + PROGRAMME_LINEUPS.keySet());
        return PROGRAMME_LINEUPS.getOrDefault(cohort, null);
    }
}
