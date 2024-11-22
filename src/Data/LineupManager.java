package Data;

import java.util.*;

public class LineupManager {
    private static final Map<String, Map<String, List<Subject>>> PROGRAMME_LINEUPS = new HashMap<>();

    static {
        initializeBCS2024Lineups();
    }

    private static void initializeBCS2024Lineups() {
        Map<String, List<Subject>> bcs202401 = new LinkedHashMap<>();
        bcs202401.put("Jan2024", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024,
                Subject.MPU3183, Subject.MPU3213
        ));
        bcs202401.put("Mar2024", List.of(
                Subject.ENG1044, Subject.CSC1202, Subject.MTH1114, Subject.PRG1203
        ));
        bcs202401.put("Aug2024", List.of(
                Subject.SEG1201, Subject.NET1014, Subject.CSC2104, Subject.WEB1201
        ));
        bcs202401.put("Jan2025", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        bcs202401.put("Mar2025", List.of(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044
        ));
        bcs202401.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs202401.put("Jan2026", List.of(
                Subject.SEG3203 // Internship
        ));
        bcs202401.put("Mar2026", List.of(
                Subject.CSC3206, Subject.Elective2, Subject.PRJ3213, Subject.Elective3
        ));
        bcs202401.put("Aug2026", List.of(
                Subject.Elective4, Subject.NET3204, Subject.PRJ3223, Subject.CSC3024
        ));
        PROGRAMME_LINEUPS.put("2024January", bcs202401); // Use correct cohort key
    }


    public static Map<String, List<Subject>> getBaseLineupForCohort(String cohortKey) {
        if (!PROGRAMME_LINEUPS.containsKey(cohortKey)) {
            System.err.println("Debug: Cohort key not found in LineupManager: " + cohortKey);
        }
        return PROGRAMME_LINEUPS.getOrDefault(cohortKey, new LinkedHashMap<>());
    }

    public static List<List<Subject>> getAllSubjectsForCohort(String cohortKey) {
        Map<String, List<Subject>> semesterLineup = PROGRAMME_LINEUPS.getOrDefault(cohortKey, new HashMap<>());

        // Convert the semester-based map to a List<List<Subject>>
        List<List<Subject>> allSemesterPlans = new ArrayList<>();
        for (Map.Entry<String, List<Subject>> entry : semesterLineup.entrySet()) {
            allSemesterPlans.add(new ArrayList<>(entry.getValue())); // Convert each semester's subjects to a list
        }
        return allSemesterPlans;
    }


}
