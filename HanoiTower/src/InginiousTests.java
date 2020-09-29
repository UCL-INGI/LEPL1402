package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import templates.*;

import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private static Random rng = new Random();

    public static int randomInt(){
        return rng.nextInt(10000);
    }

    public static Stack<Disk> buildTower(int size){
        Stack<Disk> tower = new Stack<Disk>();
        for(int i = size ; i > 0 ; i--){
            tower.push(new Disk(i));
        }
        return tower;
    }

    @Test()
    @Grade(value=3, custom=true, cpuTimeout=1000)
    @GradeFeedback(message = "Your implementation is too slow", onTimeout = true)
    public void testThree() throws CustomGradingResult{

        int size = 3;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);
        if(TowerOfHanoi.counter==1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Don't try to trick the test");
        }
        if(TowerOfHanoi.counter!=Math.pow(2, size)-1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter);
        }

        //throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter + "         " + ((int)Math.pow(2, size) -1));
        assertEquals(TowerOfHanoi.counter, (int)Math.pow(2, size) -1);

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }
            //assertEquals(i, b.pop().getI());

        assertTrue(TowerOfHanoi.rulesRespected);

    }


    @Test()
    @Grade(value=4, custom=true, cpuTimeout=3000)
    @GradeFeedback(message = "Your implementation is too slow", onTimeout = true)
    public void testFour() throws CustomGradingResult{
        int size = 4;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);
        if(TowerOfHanoi.counter==1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Don't try to trick the test");
        }
        if(TowerOfHanoi.counter!=Math.pow(2, size)-1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter);
        }

        //throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter + "         " + ((int)Math.pow(2, size) -1));
        assertEquals(TowerOfHanoi.counter, (int)Math.pow(2, size) -1);

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }
            //assertEquals(i, b.pop().getI());

        assertTrue(TowerOfHanoi.rulesRespected);
    }

    @Test()
    @Grade(value=10, custom=true, cpuTimeout=3000)
    @GradeFeedback(message = "Your implementation is too slow", onTimeout = true)
    public void tesTen() throws CustomGradingResult{
        int size = 10;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);
        if(TowerOfHanoi.counter==1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Don't try to trick the test");
        }
        if(TowerOfHanoi.counter!=Math.pow(2, size)-1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter);
        }

        //throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter + "         " + ((int)Math.pow(2, size) -1));
        assertEquals(TowerOfHanoi.counter, (int)Math.pow(2, size) -1);

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }
            //assertEquals(i, b.pop().getI());

        assertTrue(TowerOfHanoi.rulesRespected);
    }

    @Test()
    @Grade(value=20, custom=true, cpuTimeout=3000)
    @GradeFeedback(message = "Your implementation is too slow", onTimeout = true)
    public void tesTwenty() throws CustomGradingResult{
        int size = 20;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);
        if(TowerOfHanoi.counter==1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Don't try to trick the test");
        }
        if(TowerOfHanoi.counter!=Math.pow(2, size)-1){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter);
        }

        //throw new CustomGradingResult(TestStatus.FAILED, 0, "Good to see you've tried another way but the only working solution is recursive : " + TowerOfHanoi.counter + "         " + ((int)Math.pow(2, size) -1));
        assertEquals(TowerOfHanoi.counter, (int)Math.pow(2, size) -1);

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }
            //assertEquals(i, b.pop().getI());

        assertTrue(TowerOfHanoi.rulesRespected);
    }
    
    @Test()
    @Grade(value=3, cpuTimeout=1000)
    @GradeFeedback(message = "The answer given is wrong", onFail = true)
    public void testStackSize() throws CustomGradingResult{
        assertEquals(TowerOfHanoi.numberOfMoves(3), 7);
        assertEquals(TowerOfHanoi.numberOfMoves(4), 15);
        assertEquals(TowerOfHanoi.numberOfMoves(10), 1023);
        assertEquals(TowerOfHanoi.numberOfMoves(30), 1073741823);
        
    }


}
