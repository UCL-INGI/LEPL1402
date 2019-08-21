package templates;
import java.util.Stack;

import src.*;

public class TowerOfHanoi{

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

    public static void towerOfHanoi(int n, Stack<Disk> a, Stack<Disk> b, Stack<Disk> c) {
        counter++;
        checkRules(a,b,c);
        @@studentCode@@
    }
    
   public static int numberOfMoves(int stackSize){
       @@studentNumber@@
   }

}
