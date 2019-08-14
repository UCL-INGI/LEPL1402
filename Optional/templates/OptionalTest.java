package templates;

import java.util.Optional;

public class OptionalTest {

    /**
     * return an Optional<TeamLeader> object from teamLeader
     */
    public Optional<TeamLeader> createOptionalTeamLeader(TeamLeader teamLeader){
        @@student_of_nullable@@
    }

    /**
     * Increment by one the age of teamLeader
     */
    public void incAge(Optional<TeamLeader> optionalTeamLeader){
        @@student_if_present@@
    }

    /**
     * Increment by one the age of teamLeader if its age is > 15
     */
    public void incAgeIfMoreThanFifteen(Optional<TeamLeader> optionalTeamLeader){
        @@student_filter@@
    }

    /**
     * return the name of teamLeader or "No name"
     */
    public String getName(Optional<TeamLeader> optionalTeamLeader){
        @@student_map_orelse@@
    }

    /**
     * return the name of the teamLeader of the team of person or "No team leader"
     */
    public String getNameOfTeamLeader(Optional<Person> optionalPerson){
        @@student_flatmap@@
    }
}
