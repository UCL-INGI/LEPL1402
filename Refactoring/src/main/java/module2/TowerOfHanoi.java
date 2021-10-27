package module2;

import java.util.Stack;

/**
 * The tower of Hanoi is a mathematical puzzle, it consists of three rods and a number of disk of different size which can slide onto any rod.
 * The game starts with all the disks in an ascending order forming a stack on the first rod.
 * The objective of this puzzle is to move the entire stack to another rod.
 * We know, it sounds easy, but there are 3 simple rules that make the game harder than you think.
 *
 * Here they are :
 *      - You can only move one disk at a time.
 *      - Each move consists of taking the upper disk from one stack and moving it to another stack or on an empty rod.
 *      - No larger disk may be placed on top of a smaller disk.
 *
 * To help you a little bit, here is an example of how you can solve the problem with a stack of size 3.
 *
 *     .. image:: /course/LEPL1402/HanoiTower/tower-of-hanoi.png
 *        :scale: 100%
 *        :align: center
 *        :height: 384px
 *        :width: 684 px
 *
 * In this exercise we ask you to code a method that solve this puzzle for any size of stacks.
 * To represent the rods we use the java class Stack.
 * The object Disk is here to make sure you will not solve the exercise by just creating another stack.
 *
 * hint: you can call towerOfHanoi inside itself
 */
public class TowerOfHanoi{

    //BEGIN STRIP
    public static int counter=0, sizeA=0, sizeB=0, sizeC=0;
    public static boolean rulesRespected=true;

    public static void resetC(){counter=0;}

    public static void checkRules(Stack<Disk> a, Stack<Disk> b, Stack<Disk> c){
        if(!rulesRespected) return;
        Stack<Disk> tester = a;
        int diskValue=Integer.MAX_VALUE;
        for(Disk d : tester){
            if(d.getI()>diskValue){
                rulesRespected=false;
                return;
            }
            diskValue = d.getI();
        }

        tester = b;
        diskValue=Integer.MAX_VALUE;
        for(Disk d : tester){
            if(d.getI()>diskValue){
                rulesRespected=false;
                return;
            }
            diskValue = d.getI();
        }

        tester = c;
        diskValue=Integer.MAX_VALUE;
        for(Disk d : tester){
            if(d.getI()>diskValue){
                rulesRespected=false;
                return;
            }
            diskValue = d.getI();
        }
    }
    //END STRIP

    public static void towerOfHanoi(int n, Stack<Disk> a, Stack<Disk> b, Stack<Disk> c) {
        //BEGIN STRIP
        if(n != 0)
            counter++;
        checkRules(a,b,c);
        //END STRIP
        //TODO
    }
    
   public static int numberOfMoves(int stackSize){
       //TODO
       return 0;
   }

}

class Disk {

    private int i;

    public Disk(int i){
        this.i=i;
    }

    public int getI() {
        return i;
    }

}

