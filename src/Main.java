import Data.*;
import SubjectPlan.SubjectPlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Data.ProgrammeLineup;
import Data.Subject;

public class Main {
    public static void main(String[] args) {
        ProgrammeLineup january2024Lineup = ProgrammeLineup.getJanuary2024Lineup();
        System.out.println("Subjects for January 2024 Intake:");
        for (Subject subject : january2024Lineup.getSemesterSubjects()) {
            System.out.println(subject);
        }
    }
}

