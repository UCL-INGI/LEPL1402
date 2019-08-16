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
      TeamLeader tl = null;
      try{
        Optional<TeamLeader> teamLeader = test.createOptionalTeamLeader(tl);
        if(teamLeader.equals(Optional.empty()) && test.createOptionalTeamLeader(new TeamLeader("Paul", 50)) instanceof Optional){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used correctly \"ofNullable\"");
        }
      } catch(NullPointerException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Another method than \"of\" exists and return an empty Optional instead of null");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0);
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testIncAge() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tl1 = Optional.ofNullable(new TeamLeader());
      Optional<TeamLeader> tl2 = Optional.ofNullable(new TeamLeader("Paul", 30));
      try{
        test.incAge(empty); // must do nothing if well implemented or throw a NoSuchElementException
        test.incAge(tl1);
        test.incAge(tl2);
        if(tl1.map(TeamLeader::getAge).orElse(0) == 1 && tl2.map(TeamLeader::getAge).orElse(0) == 31){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used correctly ifPresent");
        }
      } catch(NoSuchElementException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using get is not a good solution for this implementation. Look for a other method of Optional.");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0);
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testIncAgeIfMoreThanFifteen() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tl1 = Optional.ofNullable(new TeamLeader());
      Optional<TeamLeader> tl2 = Optional.ofNullable(new TeamLeader("Paul", 30));
      try{
        test.incAgeIfMoreThanFifteen(empty); // must do nothing if well implemented or throw a NoSuchElementException
        test.incAgeIfMoreThanFifteen(tl1);
        test.incAgeIfMoreThanFifteen(tl2);
        if(tl1.map(TeamLeader::getAge).orElse(-1) == 0 && tl2.map(TeamLeader::getAge).orElse(0) == 31){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used correctly filter");
        }
      } catch(NoSuchElementException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using get is not a good solution for this implementation. Look for a other method of Optional.");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0);
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testGetName() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<TeamLeader> tl = Optional.ofNullable(new TeamLeader("Paul", 30));
      try{
        if(test.getName(empty).equals("No team leader") && test.getName(tl).equals("Paul")){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used correctly map");
        }
      } catch(NoSuchElementException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using get is not a good solution for this implementation. Look for a other method of Optional.");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0);
    }

    @Test
    @Grade(cpuTimeout=100, value = 1, custom=true)
    public void testGetNameOfTeamLeader() throws CustomGradingResult{
      Optional<TeamLeader> empty = Optional.empty();
      Optional<Person> personEmpty = Optional.ofNullable(new Person(Optional.ofNullable(new Team(empty))));
      Optional<Person> person =  Optional.ofNullable(new Person(Optional.ofNullable(new Team(Optional.ofNullable(new TeamLeader("Paul", 30))))));
      try{
        if(test.getNameOfTeamLeader(personEmpty).equals("No team leader") && test.getNameOfTeamLeader(person).equals("Paul")){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used correctly flatmap");
        }
      } catch(NoSuchElementException e){
        throw new CustomGradingResult(TestStatus.FAILED, 0, "Using get is not a good solution for this implementation. Look for a other method of Optional.");
      }
      throw new CustomGradingResult(TestStatus.FAILED, 0);

    }

}
