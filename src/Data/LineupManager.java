package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineupManager {
    private static final Map<String, Map<String, List<Subject>>> PROGRAMME_LINEUPS = new HashMap<>();

    static {
        // ********** BCS 2024 January Intake ********** //
        Map<String, List<Subject>> bcs202401 = new HashMap<>();
        bcs202401.put("Jan2024", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024, // MPU and Core Subject
                Subject.MPU3183, Subject.MPU3213 // Alternative MPU (Critical Thinking or Malay for Communication)
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

        PROGRAMME_LINEUPS.put("202401", bcs202401);

        Map<String, List<Subject>> bcs202403 = new HashMap<>();
        bcs202403.put("Mar2024", List.of(
                Subject.ENG1044, Subject.CSC1202, Subject.CSC1024, Subject.MTH1114
        ));
        bcs202403.put("Aug2024", List.of(
                Subject.SEG1201, Subject.PRG1203, Subject.WEB1201, Subject.CSC2104
        ));
        bcs202403.put("Jan2025", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014
        ));
        bcs202403.put("Mar2025", List.of(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044
        ));
        bcs202403.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs202403.put("Jan2026", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        bcs202403.put("Mar2026", List.of(
                Subject.PRJ3213, Subject.CSC3206, Subject.Elective2, Subject.Elective3
        ));
        bcs202403.put("Aug2026", List.of(
                Subject.Elective4, Subject.NET3204, Subject.PRJ3223, Subject.CSC3024
        ));
        bcs202403.put("Jan2027", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("202403", bcs202403);

        Map<String, List<Subject>> bcs202408 = new HashMap<>();
        bcs202408.put("Aug2024", List.of(
                Subject.CSC1024, Subject.CSC1202, Subject.ENG1044, Subject.MTH1114
        ));
        bcs202408.put("Jan2025", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014
        ));
        bcs202408.put("Mar2025", List.of(
                Subject.CSC2104, Subject.PRG1203, Subject.SEG1201, Subject.WEB1201
        ));
        bcs202408.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs202408.put("Jan2026", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.CSC2014
        ));
        bcs202408.put("Mar2026", List.of(
                Subject.ENG2044, Subject.CSC3206, Subject.PRJ3213, Subject.Elective2
        ));
        bcs202408.put("Aug2026", List.of(
                Subject.NET3204, Subject.PRJ3223, Subject.Elective3, Subject.CSC3024
        ));
        bcs202408.put("Jan2027", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("202408", bcs202408);

        Map<String, List<Subject>> bcs2024MathJan = new HashMap<>();
        bcs2024MathJan.put("Jan2024", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024, Subject.MAT1013 // MPU and Core with Math Requirement
        ));
        bcs2024MathJan.put("Mar2024", List.of(
                Subject.ENG1044, Subject.CSC1202, Subject.MTH1114, Subject.PRG1203
        ));
        bcs2024MathJan.put("Aug2024", List.of(
                Subject.SEG1201, Subject.NET1014, Subject.CSC2104, Subject.WEB1201
        ));
        bcs2024MathJan.put("Jan2025", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        bcs2024MathJan.put("Mar2025", List.of(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044
        ));
        bcs2024MathJan.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs2024MathJan.put("Jan2026", List.of(
                Subject.SEG3203 // Internship
        ));
        bcs2024MathJan.put("Mar2026", List.of(
                Subject.CSC3206, Subject.Elective2, Subject.PRJ3213, Subject.Elective3
        ));
        bcs2024MathJan.put("Aug2026", List.of(
                Subject.Elective4, Subject.NET3204, Subject.PRJ3223, Subject.CSC3024
        ));

        PROGRAMME_LINEUPS.put("2024MathJan", bcs2024MathJan);

        Map<String, List<Subject>> bcs2024MathMar = new HashMap<>();
        bcs2024MathMar.put("Mar2024", List.of(
                Subject.ENG1044, Subject.CSC1202, Subject.CSC1024, Subject.WEB1201, Subject.MAT1013
        ));
        bcs2024MathMar.put("Aug2024", List.of(
                Subject.SEG1201, Subject.PRG1203, Subject.CSC2104, Subject.MTH1114
        ));
        bcs2024MathMar.put("Jan2025", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014
        ));
        bcs2024MathMar.put("Mar2025", List.of(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044
        ));
        bcs2024MathMar.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs2024MathMar.put("Jan2026", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        bcs2024MathMar.put("Mar2026", List.of(
                Subject.CSC3206, Subject.PRJ3213, Subject.Elective2, Subject.Elective3
        ));
        bcs2024MathMar.put("Aug2026", List.of(
                Subject.Elective4, Subject.NET3204, Subject.PRJ3223, Subject.CSC3024
        ));
        bcs2024MathMar.put("Jan2027", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("2024MathMar", bcs2024MathMar);

        Map<String, List<Subject>> bcs2024MathAug = new HashMap<>();
        bcs2024MathAug.put("Aug2024", List.of(
                Subject.CSC1024, Subject.CSC1202, Subject.ENG1044, Subject.WEB1201, Subject.MAT1013
        ));
        bcs2024MathAug.put("Jan2025", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014
        ));
        bcs2024MathAug.put("Mar2025", List.of(
                Subject.CSC2104, Subject.PRG1203, Subject.SEG1201, Subject.MTH1114
        ));
        bcs2024MathAug.put("Aug2025", List.of(
                Subject.FreeElective1, Subject.FreeElective2, Subject.FreeElective3, Subject.Elective1
        ));
        bcs2024MathAug.put("Jan2026", List.of(
                Subject.MPU3222, Subject.KIAR, Subject.CSC2014
        ));
        bcs2024MathAug.put("Mar2026", List.of(
                Subject.ENG2044, Subject.CSC3206, Subject.PRJ3213, Subject.Elective2
        ));
        bcs2024MathAug.put("Aug2026", List.of(
                Subject.NET3204, Subject.PRJ3223, Subject.Elective3, Subject.CSC3024
        ));
        bcs2024MathAug.put("Jan2027", List.of(
                Subject.MPU3412 // MPU elective, typically last
        ));
        bcs2024MathAug.put("Mar2027", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("2024MathAug", bcs2024MathAug);

        Map<String, List<Subject>> bcsDirectEntryJan2024 = new HashMap<>();
        bcsDirectEntryJan2024.put("Jan2024", List.of(
                Subject.BIS2212, Subject.MPU3193, Subject.MPU3183, Subject.ENG2042
        ));
        bcsDirectEntryJan2024.put("Mar2024", List.of(
                Subject.CSC2103, Subject.SEG2202, Subject.PRG2104, Subject.CSC2014
        ));
        bcsDirectEntryJan2024.put("Aug2024", List.of(
                Subject.CSC3206, Subject.Elective1, Subject.Elective2
        ));
        bcsDirectEntryJan2024.put("Jan2025", List.of(
                Subject.SEG3203 // Internship
        ));
        bcsDirectEntryJan2024.put("Mar2025", List.of(
                Subject.PRJ3213, Subject.MPU3412, Subject.MPU3222, Subject.NET3204
        ));
        bcsDirectEntryJan2024.put("Aug2025", List.of(
                Subject.PRJ3223, Subject.Elective3, Subject.Elective4
        ));

        PROGRAMME_LINEUPS.put("DirectEntryJan2024", bcsDirectEntryJan2024);

        // ********** BCS Direct Entry Y2 March 2024 Intake ********** //
        Map<String, List<Subject>> bcsDirectEntryMar2024 = new HashMap<>();
        bcsDirectEntryMar2024.put("Mar2024", List.of(
                Subject.CSC2103, Subject.PRG2104, Subject.CSC2014
        ));
        bcsDirectEntryMar2024.put("Aug2024", List.of(
                Subject.CSC3024, Subject.NET3204, Subject.Elective3, Subject.SEG2202
        ));
        bcsDirectEntryMar2024.put("Jan2025", List.of(
                Subject.MPU3193, Subject.MPU3183, Subject.MPU3222, Subject.MPU3412
        ));
        bcsDirectEntryMar2024.put("Mar2025", List.of(
                Subject.PRJ3213, Subject.CSC3206, Subject.MPU3312, Subject.Elective2
        ));
        bcsDirectEntryMar2024.put("Aug2025", List.of(
                Subject.PRJ3223, Subject.Elective1, Subject.Elective4
        ));
        bcsDirectEntryMar2024.put("Jan2026", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("DirectEntryMar2024", bcsDirectEntryMar2024);

        // ********** BCS Direct Entry Y2 August 2024 Intake ********** //
        Map<String, List<Subject>> bcsDirectEntryAug2024 = new HashMap<>();
        bcsDirectEntryAug2024.put("Aug2024", List.of(
                Subject.MPU3193, Subject.MPU3203, Subject.NET3204
        ));
        bcsDirectEntryAug2024.put("Jan2025", List.of(
                Subject.CSC2014, Subject.PRG2104, Subject.SEG2202
        ));
        bcsDirectEntryAug2024.put("Mar2025", List.of(
                Subject.CSC3206, Subject.Elective1, Subject.MPU3222
        ));
        bcsDirectEntryAug2024.put("Aug2025", List.of(
                Subject.PRJ3213, Subject.Elective2, Subject.CSC3024
        ));
        bcsDirectEntryAug2024.put("Jan2026", List.of(
                Subject.PRJ3223, Subject.Elective3, Subject.Elective4
        ));
        bcsDirectEntryAug2024.put("Mar2026", List.of(
                Subject.SEG3203 // Internship
        ));

        PROGRAMME_LINEUPS.put("DirectEntryAug2024", bcsDirectEntryAug2024);
    }

    public static List<Subject> getLineup(String cohort, String semester) {
        return PROGRAMME_LINEUPS.getOrDefault(cohort, new HashMap<>()).getOrDefault(semester, new ArrayList<>());
    }

    public static Map<String, List<Subject>> getLineupForCohort(String cohort) {
        return PROGRAMME_LINEUPS.getOrDefault(cohort, new HashMap<>());
    }

    public static void addSemesterLineup(String cohort, String semester, List<Subject> subjects) {
        PROGRAMME_LINEUPS.computeIfAbsent(cohort, k -> new HashMap<>()).put(semester, new ArrayList<>(subjects));
    }

    public static void printAllLineups() {
        PROGRAMME_LINEUPS.forEach((cohort, semesters) -> {
            System.out.println("Cohort: " + cohort);
            semesters.forEach((semester, subjects) -> {
                System.out.println("  Semester: " + semester);
                subjects.forEach(subject -> System.out.println("    " + subject));
            });
        });
    }
}
