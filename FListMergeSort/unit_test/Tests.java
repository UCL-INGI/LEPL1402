import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.Arrays;



@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class Tests{

    private static Random rng = new Random();

    public static int randomInt(){
        return rng.nextInt(10000);
    }

    private static boolean isSorted(FList<Integer> fl) {
        if(fl.isEmpty()) return true;

        if(fl.tail().isEmpty()) return true;

        if(fl.head() <= fl.tail().head()) return isSorted(fl.tail());
        else return false;

    }

    @Test()
    @Grade(value=5, custom=true, cpuTimeout=1000)
    public void testGlobal() throws CustomGradingResult{

        FList<Integer> fl = FList.nil();
        int[] array = new int[1000];
        for(int i = 0 ; i < 1000 ; i++){
            int r = randomInt();
            fl = fl.cons(r);
            array[i] = r;
        }
        Arrays.sort(array);

        FList<Integer> supposedSorted = FListMerge.mergeSort(fl);

        if(!isSorted(supposedSorted)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "mergeSort does not sort correctly");
        }

        FList<Integer> copy = supposedSorted;
        for(int i = 0 ; i < 1000 ; i++){
            if(array[i]!=copy.head()) throw new CustomGradingResult(TestStatus.FAILED, 0 , "Some element have changed");
            copy = copy.tail();
        }

    }


    @Test()
    @Grade(value=5, custom=true, cpuTimeout=10000)
    @GradeFeedback(message = "Your implementation is too slow", onTimeout = true)
    public void testComplexity() throws CustomGradingResult{

        int size = 10000;

        FList<Integer> fl = FList.nil();
        int[] array = new int[size];
        for(int i = 0 ; i < size ; i++){
            int r = randomInt();
            fl = fl.cons(r);
            array[i] = r;
        }
        Arrays.sort(array);
        long t = System.currentTimeMillis();
        FList<Integer> supposedSorted = FListMerge.mergeSort(fl);
        long timeTaken = System.currentTimeMillis()-t;
        FList<Integer> copy = supposedSorted;
        for(int i = 0 ; i < size ; i++){
            if(array[i]!=copy.head()) throw new CustomGradingResult(TestStatus.FAILED, 0 , "Some element have changed");
            copy = copy.tail();
        }
        if(timeTaken>4500){
            throw new CustomGradingResult(TestStatus.TIMEOUT, 0 , "Your implementation is too slow : " + timeTaken);
        }
    }

}
