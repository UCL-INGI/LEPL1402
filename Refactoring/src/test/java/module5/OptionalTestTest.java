package module5;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.*;

public class OptionalTestTest {

    private OptionalTest test = new OptionalTest();

    @Test
    public void basicTestCreateOptionalTeamLeader() {
        Optional<OptionalTest.TeamLeader> optionalTeamLeaderEmpty = test.createOptionalTeamLeader(null);
        Optional<OptionalTest.TeamLeader> optionalTeamLeaderPaul = test.createOptionalTeamLeader(new OptionalTest.TeamLeader("Paul", 50));
        assertEquals(optionalTeamLeaderEmpty, Optional.empty());
        assertTrue(optionalTeamLeaderPaul instanceof Optional);
    }

    //BEGIN STRIP
    @Test
    public void testCreateOptionalTeamLeader() {
        OptionalTest.TeamLeader tlNull = null;
        OptionalTest.TeamLeader tlPaul = new OptionalTest.TeamLeader("Paul", 50);
        try {
            Optional<OptionalTest.TeamLeader> teamLeaderNull = test.createOptionalTeamLeader(tlNull);
            Optional<OptionalTest.TeamLeader> teamLeaderPaul = test.createOptionalTeamLeader(tlPaul);
            if(teamLeaderNull.equals(Optional.empty())) {
                if(!(teamLeaderPaul instanceof Optional)) {
                    fail("Your code did not return a valid Optional<OptionalTest.TeamLeader>");
                    // throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'ofNullable'");
                }
            }
            fail("Your code failed to handle a null input");
        }
        catch(NullPointerException e){
            fail("There's another method than 'of' that will return an empty Optional instead of null");
        }
    }

    @Test
    public void testIncAge() {
        Optional<OptionalTest.TeamLeader> empty = Optional.empty();
        Optional<OptionalTest.TeamLeader> tl1 = Optional.ofNullable(new OptionalTest.TeamLeader());
        Optional<OptionalTest.TeamLeader> tl2 = Optional.ofNullable(new OptionalTest.TeamLeader("Paul", 30));
        try {
            test.incAge(empty); // must do nothing if well implemented or throw a NoSuchElementException
            test.incAge(tl1);
            test.incAge(tl2);
            if(tl1.map(OptionalTest.TeamLeader::getAge).orElse(0) == 1 && tl2.map(OptionalTest.TeamLeader::getAge).orElse(0) == 31){
                // throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'ifPresent'");
            }
        }
        catch(NoSuchElementException e) {
            fail("Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
        }
        fail("Your code did not increment the team leader's age as expected");
    }

    @Test
    public void testIncAgeIfMoreThanFifteen() {
        Optional<OptionalTest.TeamLeader> empty = Optional.empty();
        Optional<OptionalTest.TeamLeader> tlAge0 = Optional.ofNullable(new OptionalTest.TeamLeader());
        Optional<OptionalTest.TeamLeader> tlAge15 = Optional.ofNullable(new OptionalTest.TeamLeader("Pierre", 15));
        Optional<OptionalTest.TeamLeader> tlAge30 = Optional.ofNullable(new OptionalTest.TeamLeader("Paul", 30));
        try {
            test.incAgeIfMoreThanFifteen(empty); // must do nothing if well implemented or throw a NoSuchElementException
            test.incAgeIfMoreThanFifteen(tlAge0);
            test.incAgeIfMoreThanFifteen(tlAge15);
            test.incAgeIfMoreThanFifteen(tlAge30);
            if(tlAge0.map(OptionalTest.TeamLeader::getAge).orElse(-1) == 0) {
                if (tlAge15.map(OptionalTest.TeamLeader::getAge).orElse(0) == 15) {
                    if (tlAge30.map(OptionalTest.TeamLeader::getAge).orElse(0) == 31) {
                        // throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'filter'");
                    }
                    fail("Your code did not increment the age of a team leader whose age is > 15");
                }
                fail("Your code incremented the age of a team leader whose age is exactly 15");
            }
            fail("Your code incremented the age of a team leader whose age is < 15");
        }
        catch(NoSuchElementException e) {
            fail("Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
        }
    }

    @Test
    public void testGetName() {
        Optional<OptionalTest.TeamLeader> empty = Optional.empty();
        Optional<OptionalTest.TeamLeader> tl = Optional.ofNullable(new OptionalTest.TeamLeader("Paul", 30));
        try {
            if(test.getName(empty).equals("No team leader")) {
                if(test.getName(tl).equals("Paul")) {
                    // throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'map'");
                }
                fail("Your code doesn't return the correct name");
            }
            fail("Your code doesn't return \"No team leader\" when expected to");
        }
        catch(NoSuchElementException e) {
            fail("Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
        }
    }

    @Test
    public void testGetNameOfTeamLeader() {
        Optional<OptionalTest.TeamLeader> empty = Optional.empty();
        Optional<OptionalTest.Person> personEmpty = Optional.ofNullable(new OptionalTest.Person(Optional.ofNullable(new OptionalTest.Team(empty))));
        Optional<OptionalTest.Person> person =  Optional.ofNullable(new OptionalTest.Person(Optional.ofNullable(new OptionalTest.Team(Optional.ofNullable(new OptionalTest.TeamLeader("Paul", 30))))));
        try {
            if(test.getNameOfTeamLeader(personEmpty).equals("No team leader")) {
                if(test.getNameOfTeamLeader(person).equals("Paul")) {
                    // throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'flatmap'");
                }
                fail("Your code doesn't return the correct name");
            }
            fail("Your code doesn't return \"No team leader\" when expected to");
        }
        catch(NoSuchElementException e) {
            fail("Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
        }
    }
    //END STRIP
}
