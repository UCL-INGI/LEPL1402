package templates;

import java.util.Optional;

public class OptionalTest {

    /**
     * Returns an Optional<TeamLeader> object from teamLeader
     */
    public Optional<TeamLeader> createOptionalTeamLeader(TeamLeader teamLeader) {
        @@student_of_nullable@@
    }

    /**
     * Increments the age of teamLeader by one
     */
    public void incAge(Optional<TeamLeader> optionalTeamLeader) {
        @@student_if_present@@
    }

    /**
     * Increments the age of teamLeader by one only if its age is > 15
     */
    public void incAgeIfMoreThanFifteen(Optional<TeamLeader> optionalTeamLeader) {
        @@student_filter@@
    }

    /**
     * Returns the name of the teamLeader or "No team leader"
     */
    public String getName(Optional<TeamLeader> optionalTeamLeader) {
        @@student_map_orelse@@
    }

    /**
     * Returns the name of the teamLeader of the team of the person or "No team leader"
     */
    public String getNameOfTeamLeader(Optional<Person> optionalPerson) {
        @@student_flatmap@@
    }
}
