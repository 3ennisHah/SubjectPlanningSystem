package SubjectPlan;

import Data.Subject;

import java.util.HashSet;
import java.util.Set;

public class ProgrammeLineup {
    private String cohort;
    private boolean isDirect;
    private Set<Subject> coreSubjects = new HashSet<>();
    private Set<Subject> disciplineElectivesY2 = new HashSet<>();
    private Set<Subject> disciplineElectivesY3 = new HashSet<>();
    private Set<Subject> freeElectives = new HashSet<>();
    private Set<Subject> compulsorySubjects = new HashSet<>();

    public ProgrammeLineup(String cohort, boolean isDirect) {
        this.cohort = cohort;
        this.isDirect = isDirect;
    }

    public void addCoreSubject(Subject subject) {
        coreSubjects.add(subject);
    }

    public void addDisciplineElectiveY2(Subject subject) {
        disciplineElectivesY2.add(subject);
    }

    public void addDisciplineElectiveY3(Subject subject) {
        disciplineElectivesY3.add(subject);
    }

    public void addFreeElective(Subject subject) {
        freeElectives.add(subject);
    }

    public void addCompulsorySubject(Subject subject) {
        compulsorySubjects.add(subject);
    }

    public Set<Subject> getCoreSubjects() {
        return new HashSet<>(coreSubjects); // Return a new mutable set
    }

    public Set<Subject> getDisciplineElectivesY2() {
        return disciplineElectivesY2;
    }

    public Set<Subject> getDisciplineElectivesY3() {
        return disciplineElectivesY3;
    }

    public Set<Subject> getFreeElectives() {
        return freeElectives;
    }

    public Set<Subject> getCompulsorySubjects() {
        return compulsorySubjects;
    }
}
