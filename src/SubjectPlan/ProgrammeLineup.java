package Data;

import java.util.ArrayList;
import java.util.List;

public class ProgrammeLineup {
    private List<Subject> semesterSubjects;

    public ProgrammeLineup() {
        this.semesterSubjects = new ArrayList<>();
    }

    public void addSubjectsForSemester(Subject... subjects) {
        for (Subject subject : subjects) {
            semesterSubjects.add(subject);
        }
    }

    public List<Subject> getSemesterSubjects() {
        return semesterSubjects;
    }

    // ********** Category: BCS 2024 ********** //

    public static ProgrammeLineup getJanuary2024Lineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024 // Jan 2024 (Short Semester)
        );
        lineup.addSubjectsForSemester(
                Subject.ENG1044, Subject.CSC1202, Subject.MTH1114, Subject.PRG1203 // Mar 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.SEG1201, Subject.NET1014, Subject.CSC2104, Subject.WEB1201 // Aug 2024 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3222, Subject.SEG2202, Subject.MPU3412 // Jan 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044 // Mar 2025 Sem 5
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2026 Sem 7 Internship
        );
        lineup.addSubjectsForSemester(
                Subject.NET3204, Subject.PRJ3223, Subject.CSC3024 // Aug 2026 Sem 9
        );

        return lineup;
    }

    public static ProgrammeLineup getMarch2024Lineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.ENG1044, Subject.CSC1202, Subject.CSC1024, Subject.MTH1114 // Mar 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.SEG1201, Subject.PRG1203, Subject.WEB1201, Subject.CSC2104 // Aug 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014 // Jan 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044 // Mar 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.PRG2205, Subject.CSC2044 // Aug 2025 Sem 5 Electives + Free
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3222, Subject.SEG2202, Subject.MPU3412 // Jan 2026 Sem 6
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3206, Subject.PRJ3213, Subject.NET3204 // Mar 2026 Sem 7 Electives
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3223, Subject.CSC3024, Subject.NET3204 // Aug 2026 Sem 8
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2027 Internship
        );

        return lineup;
    }

    public static ProgrammeLineup getAugust2024Lineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.CSC1024, Subject.CSC1202, Subject.ENG1044, Subject.MTH1114 // Aug 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014 // Jan 2025 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2104, Subject.PRG1203, Subject.SEG1201, Subject.WEB1201 // Mar 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044 // Mar 2026 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.CSC3034, Subject.PRG3014 // Aug 2026 Sem 5 Free & Electives
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3206, Subject.PRJ3213 // Mar 2027 Sem 6 Electives & Capstone 1
        );

        return lineup;
    }

    public static ProgrammeLineup getJanuary2024BCSMathLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.CSC1024, Subject.MAT1013 // Jan 2024 (Short Semester)
        );
        lineup.addSubjectsForSemester(
                Subject.ENG1044, Subject.CSC1202, Subject.MTH1114, Subject.PRG1203 // Mar 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.SEG1201, Subject.NET1014, Subject.CSC2104, Subject.WEB1201 // Aug 2024 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3222, Subject.SEG2202, Subject.MPU3412 // Jan 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044 // Mar 2025 Sem 5
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.PRG2205, Subject.CSC2044 // Aug 2025 Sem 6 Electives + Free
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2026 Sem 7 Internship
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3206, Subject.PRJ3213, Subject.NET3204 // Mar 2026 Sem 8 Electives
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3223, Subject.CSC3024, Subject.NET3204 // Aug 2026 Sem 9
        );

        return lineup;
    }

    public static ProgrammeLineup getMarch2024BCSMathLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.ENG1044, Subject.CSC1202, Subject.CSC1024, Subject.WEB1201, Subject.MAT1013 // Mar 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.SEG1201, Subject.PRG1203, Subject.CSC2104, Subject.MTH1114 // Aug 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014 // Jan 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.CSC2014, Subject.PRG2104, Subject.ENG2044 // Mar 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.PRG2205, Subject.CSC2044 // Aug 2025 Sem 5 Electives + Free
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3222, Subject.SEG2202 // Jan 2026 Sem 6
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3213, Subject.CSC3206, Subject.NET3204 // Mar 2026 Sem 7 Capstone & Electives
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3223, Subject.CSC3024, Subject.NET3204 // Aug 2026 Sem 8 Capstone 2
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2027 Sem 9 Internship
        );

        return lineup;
    }

    public static ProgrammeLineup getAugust2024BCSMathLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.CSC1024, Subject.CSC1202, Subject.ENG1044, Subject.WEB1201, Subject.MAT1013 // Aug 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.NET1014 // Jan 2025 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2104, Subject.PRG1203, Subject.SEG1201, Subject.MTH1114 // Mar 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.PRG2205, Subject.CSC2044 // Aug 2025 Sem 4 Free Electives + Electives
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3222, Subject.SEG2202 // Jan 2026 Sem 5 Core subjects
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2014, Subject.PRG2104, Subject.ENG2044, Subject.CSC3206 // Mar 2026 Sem 6
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3213, Subject.NET3204, Subject.CSC3024 // Aug 2026 Sem 7 Capstone & Electives
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2027 Sem 8 Internship
        );
        lineup.addSubjectsForSemester(
                Subject.PRJ3223, Subject.CSC2103, Subject.NET3204 // Mar 2027 Sem 9
        );

        return lineup;
    }

    public static ProgrammeLineup getJanuary2024BCSDirectEntryLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.BIS2212, Subject.MPU3193, Subject.MPU3203 // Jan 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.SEG2202, Subject.PRG2104, Subject.ENG2044, Subject.CSC2104 // Mar 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2014, Subject.CSC3206, Subject.DECN2014, Subject.CSC3024, Subject.NET2201 // Aug 2024 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2025 Sem 4 Internship
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3206, Subject.PRG3014, Subject.PRG2205, Subject.PRJ3213, Subject.MPU3412 // Mar 2025 Sem 5
        );
        lineup.addSubjectsForSemester(
                Subject.ETP2014, Subject.CSC3034, Subject.NET3204, Subject.PRJ3223 // Aug 2025 Sem 6
        );

        return lineup;
    }

    public static ProgrammeLineup getMarch2024BCSDirectEntryLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.CSC2103, Subject.PRG2104, Subject.ENG2044, Subject.CSC2014 // Mar 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.PRG2205, Subject.SEG2202, Subject.MPU3412 // Aug 2024 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.MPU3222, Subject.MPU3312 // Jan 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3206, Subject.PRG3014, Subject.CSC3034, Subject.PRJ3213 // Mar 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3024, Subject.NET3204, Subject.PRJ3223, Subject.CSC3064, Subject.PRG2214 // Aug 2025 Sem 5
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2026 Sem 6 Internship
        );

        return lineup;
    }

    public static ProgrammeLineup getAugust2024BCSDirectEntryLineup() {
        ProgrammeLineup lineup = new ProgrammeLineup();

        lineup.addSubjectsForSemester(
                Subject.DECN2014, Subject.ETP2014, Subject.MKT2224, Subject.SEG2202, Subject.MPU3412 // Aug 2024 Sem 1
        );
        lineup.addSubjectsForSemester(
                Subject.MPU3193, Subject.MPU3203, Subject.MPU3222, Subject.MPU3312 // Jan 2025 Sem 2
        );
        lineup.addSubjectsForSemester(
                Subject.CSC2014, Subject.ENG2044, Subject.PRG2104, Subject.CSC2103 // Mar 2025 Sem 3
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3034, Subject.NET3204, Subject.PRJ3213, Subject.CSC3064 // Aug 2025 Sem 4
        );
        lineup.addSubjectsForSemester(
                Subject.SEG3203 // Jan 2026 Sem 5 Internship
        );
        lineup.addSubjectsForSemester(
                Subject.CSC3024, Subject.PRG3014, Subject.PRJ3223, Subject.PRG2214, Subject.CSC3206, Subject.MPU3412 // Mar 2026 Sem 6
        );

        return lineup;
    }


}
