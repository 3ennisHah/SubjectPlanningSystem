package Data;

import SubjectPlan.ProgrammeLineup;

import java.util.HashMap;
import java.util.Map;

public class LineupConfigurations {
    public static final Map<String, ProgrammeLineup> PROGRAMME_LINEUPS = new HashMap<>();

    static {
        ProgrammeLineup bcs202401 = new ProgrammeLineup("202401", false);
        bcs202401.addCoreSubject(new Subject("CSC1024", "Programming Principles", 4, new String[]{}, true));
        bcs202401.addCoreSubject(new Subject("ENG1044", "English for Computer Technology Studies", 4, new String[]{}, false));
        // Add more subjects as per `BCSLineup2024.txt`

        PROGRAMME_LINEUPS.put("202401", bcs202401);
    }

    public static ProgrammeLineup getLineup(String cohort) {
        return PROGRAMME_LINEUPS.get(cohort);
    }
}
