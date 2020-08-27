package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Optional;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private OptionalTest test = new OptionalTest();

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testCreateOptionalTeamLeader() throws CustomGradingResult{
      TeamLeader tlNull = null;
      TeamLeader tlPaul = new TeamLeader("Paul", 50);
      try {
        Optional<TeamLeader> teamLeaderNull = test.createOptionalTeamLeader(tlNull);
        Optional<TeamLeader> teamLeaderPaul = test.createOptionalTeamLeader(tlPaul);
        if(teamLeaderNull.equals(Optional.empty())) {
          if(teamLeaderPaul instanceof Optional) {
            throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'ofNullable'");
          }
          throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code did not return a valid Optional<TeamLeader>");
        }
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code failed to handle a null input");
      }
      catch(NullPointerException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "There's another method than 'of' that will return an empty Optional instead of null");
      }
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testIncAge() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tl1 = Optional.ofNullable(new TeamLeader());
      Optional<TeamLeader> tl2 = Optional.ofNullable(new TeamLeader("Paul", 30));
      try {
        test.incAge(empty); // must do nothing if well implemented or throw a NoSuchElementException
        test.incAge(tl1);
        test.incAge(tl2);
        if(tl1.map(TeamLeader::getAge).orElse(0) == 1 && tl2.map(TeamLeader::getAge).orElse(0) == 31){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'ifPresent'");
        }
      }
      catch(NoSuchElementException e) {
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code did not increment the team leader's age as expected");
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testIncAgeIfMoreThanFifteen() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tlAge0 = Optional.ofNullable(new TeamLeader());
      Optional<TeamLeader> tlAge15 = Optional.ofNullable(new TeamLeader("Pierre", 15));
      Optional<TeamLeader> tlAge30 = Optional.ofNullable(new TeamLeader("Paul", 30));
      try {
        test.incAgeIfMoreThanFifteen(empty); // must do nothing if well implemented or throw a NoSuchElementException
        test.incAgeIfMoreThanFifteen(tlAge0);
        test.incAgeIfMoreThanFifteen(tlAge15);
        test.incAgeIfMoreThanFifteen(tlAge30);
        if(tlAge0.map(TeamLeader::getAge).orElse(-1) == 0) {
          if (tlAge15.map(TeamLeader::getAge).orElse(0) == 15) {
            if (tlAge30.map(TeamLeader::getAge).orElse(0) == 31) {
              throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'filter'");
            }
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code did not increment the age of a team leader whose age is > 15");
          }
          throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code incremented the age of a team leader whose age is exactly 15");
        }
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code incremented the age of a team leader whose age is < 15");
      }
      catch(NoSuchElementException e) {
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
      }
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testGetName() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tl = Optional.ofNullable(new TeamLeader("Paul", 30));
      try {
        if(test.getName(empty).equals("No team leader")) {
          if(test.getName(tl).equals("Paul")) {
              throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'map'");
          }
          throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code doesn't return the correct name");
        }
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code doesn't return \"No team leader\" when expected to");
      }
      catch(NoSuchElementException e) {
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
      }
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testGetNameOfTeamLeader() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<Person> personEmpty = Optional.ofNullable(new Person(Optional.ofNullable(new Team(empty))));
      Optional<Person> person =  Optional.ofNullable(new Person(Optional.ofNullable(new Team(Optional.ofNullable(new TeamLeader("Paul", 30))))));
      try {
        if(test.getNameOfTeamLeader(personEmpty).equals("No team leader")) {
          if(test.getNameOfTeamLeader(person).equals("Paul")) {
            throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've correctly used 'flatmap'");
          }
          throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code doesn't return the correct name");
        }
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Your code doesn't return \"No team leader\" when expected to");
      }
      catch(NoSuchElementException e) {
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using 'get' is not a good solution for this implementation. Look for another method of Optional objects.");
      }
    }
}
