package Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LineupManager {
    private static final Map<String, Map<String, List<Subject>>> PROGRAMME_LINEUPS = new HashMap<>();

    static {
        initializeLineups();
    }

    private static void initializeLineups() {
        initializeBCS2024Lineups();
        initializeBCS2024MathLineups();
        initializeDirectEntryLineups();
    }

    private static void initializeBCS2024Lineups() {
        Map<String, List<Subject>> bcs202401 = new HashMap<>();
        bcs202401.put("Jan2024", createSubjectList(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024,
                Subject.MPU3183, Subject.MPU3213
        ));
        bcs202401.put("Mar2024", createSubjectList(
                Subject.ENG1044, Subject.CSC1202, Subject.MTH1114, Subject.PRG1203
        ));
        bcs202401.put("Aug2024", createSubjectList(
                Subject.SEG1201, Subject.NET1014, Subject.CSC2104, Subject.WEB1201
        ));
        bcs202401.put("Jan2025", createSubjectList(
                Subject.MPU3222, Subject.KIAR, Subject.SEG2202, Subject.MPU3412
        ));
        PROGRAMME_LINEUPS.put("202401", bcs202401);
    }

    private static void initializeBCS2024MathLineups() {
        Map<String, List<Subject>> bcs2024MathJan = new HashMap<>();
        bcs2024MathJan.put("Jan2024", createSubjectList(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024, Subject.MAT1013
        ));
        PROGRAMME_LINEUPS.put("2024Math01", bcs2024MathJan);
    }

    private static void initializeDirectEntryLineups() {
        Map<String, List<Subject>> bcsDirectEntryJan2024 = new HashMap<>();
        bcsDirectEntryJan2024.put("Jan2024", createSubjectList(
                Subject.BIS2212, Subject.MPU3193, Subject.MPU3183, Subject.ENG2042
        ));
        PROGRAMME_LINEUPS.put("DirectEntry202401", bcsDirectEntryJan2024);
    }

    private static List<Subject> createSubjectList(Subject... subjects) {
        List<Subject> subjectList = new ArrayList<>();
        for (Subject subject : subjects) {
            if (subject != null) {
                subjectList.add(subject);
            } else {
                System.err.println("Warning: Null subject found and skipped.");
            }
        }
        return subjectList;
    }

    public static List<Subject> getLineup(String cohort, String semester) {
        return PROGRAMME_LINEUPS.getOrDefault(cohort, new HashMap<>())
                .getOrDefault(semester, new ArrayList<>());
    }

    public static Map<String, List<Subject>> getLineupForCohort(String cohort) {
        return PROGRAMME_LINEUPS.getOrDefault(cohort, new HashMap<>());
    }
}
