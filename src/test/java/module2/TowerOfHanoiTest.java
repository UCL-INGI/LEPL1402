package module2;

import org.junit.Test;

import java.util.Random;
import java.util.Stack;

import static org.junit.Assert.*;


public class TowerOfHanoiTest {

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
    public void testThree() {

        int size = 3;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);

        //BEGIN STRIP
        assertNotEquals("Don't try to trick the test", 1, TowerOfHanoi.counter);
        assertEquals("Good to see you've tried another way but the only working solution is recursive : ", TowerOfHanoi.counter, (int)Math.pow(2, size) -1);
        //END STRIP

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }

        //BEGIN STRIP
        //assertEquals(i, b.pop().getI());
        assertTrue(TowerOfHanoi.rulesRespected);
        //END STRIP
    }

    @Test()
    public void testFour() {

        int size = 4;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);

        //BEGIN STRIP
        assertNotEquals("Don't try to trick the test", 1, TowerOfHanoi.counter);
        assertEquals("Good to see you've tried another way but the only working solution is recursive : ", TowerOfHanoi.counter, (int)Math.pow(2, size) -1);
        //END STRIP

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }

        //BEGIN STRIP
        //assertEquals(i, b.pop().getI());
        assertTrue(TowerOfHanoi.rulesRespected);
        //END STRIP
    }

    @Test()
    public void testTen() {

        int size = 10;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);

        //BEGIN STRIP
        assertNotEquals("Don't try to trick the test", 1, TowerOfHanoi.counter);
        assertEquals("Good to see you've tried another way but the only working solution is recursive : ", TowerOfHanoi.counter, (int)Math.pow(2, size) -1);
        //END STRIP

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }

        //BEGIN STRIP
        //assertEquals(i, b.pop().getI());
        assertTrue(TowerOfHanoi.rulesRespected);
        //END STRIP
    }

    @Test()
    public void testTwenty() {

        int size = 20;

        TowerOfHanoi.resetC();
        Stack<Disk> a = buildTower(size);
        Stack<Disk> b = new Stack<Disk>();
        Stack<Disk> c = new Stack<Disk>();

        TowerOfHanoi.towerOfHanoi(size, a, b, c);

        //BEGIN STRIP
        assertNotEquals("Don't try to trick the test", 1, TowerOfHanoi.counter);
        assertEquals("Good to see you've tried another way but the only working solution is recursive : ", TowerOfHanoi.counter, (int)Math.pow(2, size) -1);
        //END STRIP

        for(int i = 1; i<=size; i++){
            assertEquals(i, c.pop().getI());
        }

        //BEGIN STRIP
        //assertEquals(i, b.pop().getI());
        assertTrue(TowerOfHanoi.rulesRespected);
        //END STRIP
    }

    //BEGIN STRIP
    @Test()
    public void testStackSize() {
        assertEquals(TowerOfHanoi.numberOfMoves(3), 7);
        assertEquals(TowerOfHanoi.numberOfMoves(4), 15);
        assertEquals(TowerOfHanoi.numberOfMoves(10), 1023);
        assertEquals(TowerOfHanoi.numberOfMoves(30), 1073741823);
    }
    //END STRIP

}
