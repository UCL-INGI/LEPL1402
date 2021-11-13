package module5;

import java.util.Optional;

/**
 * The 8th version of java introduced the Optional object,
 * which allows us to avoid NullPointerExceptions in our code without being forced to perform a ton of checks on nullable objects.
 *
 * For this task, we give you three small classes (Person, Team and TeamLeader) and ask you to implement the different methods of OptionalTest.
 * All these methods can be implemented with just one line of code thanks to the different methods of Optional objects and lambda expressions.
 * You're therefore strongly encouraged to read through the Optional documentation.
 *      - Optional: https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
 *
 * You're not allow to use new TeamLeader() in these exercises.
 */
public class OptionalTest {
    /**
     * Returns an Optional<TeamLeader> object from teamLeader
     */
    public Optional<TeamLeader> createOptionalTeamLeader(TeamLeader teamLeader) {
        return null;
    }

    /**
     * Increments the age of teamLeader by one
     */
    public void incAge(Optional<TeamLeader> optionalTeamLeader) {

    }

    /**
     * Increments the age of teamLeader by one only if its age is > 15
     */
    public void incAgeIfMoreThanFifteen(Optional<TeamLeader> optionalTeamLeader) {

    }

    /**
     * Returns the name of the teamLeader or "No team leader"
     */
    public String getName(Optional<TeamLeader> optionalTeamLeader) {
        return null;
    }

    /**
     * Returns the name of the teamLeader of the team of the person or "No team leader"
     */
    public String getNameOfTeamLeader(Optional<Person> optionalPerson) {
        return null;
    }

    static class Person {
        Optional<Team> team;

        public Person(){}

        public Person(Optional<Team> team){
            this.team = team;
        }

        public Optional<Team> getTeam(){
            return this.team;
        }

        public void setTeam(Optional<Team> team){
            this.team = team;
        }
    }

    static class Team {
        Optional<TeamLeader> teamLeader;
        public Team(){
        }

        public Team(Optional<TeamLeader> teamLeader){
            this.teamLeader = teamLeader;
        }

        public Optional<TeamLeader> getTeamLeader(){
            return this.teamLeader;
        }

        public void setTeamLeader(Optional<TeamLeader> teamLeader){
            this.teamLeader = teamLeader;
        }
    }

    static class TeamLeader {

        private String name;
        private int age;

        public TeamLeader(){
            this.age = 0;
        }

        public TeamLeader(String name, int age){
            this.name = name;
            this.age = age;
        }

        public String getName(){
            return this.name;
        }

        public int getAge(){
            return this.age;
        }

        public void setAge(int age){
            this.age = age;
        }
    }
}
