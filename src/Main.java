import Data.*;
import SubjectPlan.SubjectPlanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Subject> availableSubjects = new ArrayList<>(Arrays.asList(
                new Subject("CSC1024", "Programming Principles", 4, new String[]{}, true),
                new Subject("ENG1044", "English for Computer Technology Studies", 4, new String[]{}, false),
                new Subject("MATH101", "Mathematics", 3, new String[]{}, true),
                new Subject("PHYS101", "Physics", 4, new String[]{"MATH101"}, true),
                new Subject("CS101", "Introduction to CS", 4, new String[]{}, true),
                new Subject("CS102", "Advanced CS", 4, new String[]{"CS101"}, true)
        ));

        Student student = new Student("S12345", "John Doe");
        student.addCompletedSubject(new Subject("MATH101", "Mathematics", 3, new String[]{}, true));

        SubjectPlanner planner = new SubjectPlanner();
        planner.planSubjects("202401", student, availableSubjects);
    }
}
