package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import templates.*;

import static org.junit.Assert.*;

/*
 * Most of the code of this test suite is taken (and adapted) from an exercise of LSINF1121 course.
 * Original authors : Pierre Schaus, John Aoga
 * Modified by : Henri Piron
 */
@RunWith(GradingRunner.class)
public class InginiousTests {

    //{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1}

    @Test()
    @Grade(value=1, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "The depth you return on the example is not correct")
    public void testExampleValley(){

        int[] example = new int[]{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1};

        int[] result = Valley.valley(example);

        assertEquals(5, result[0]);
    }

    @Test()
    @Grade(value=1, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "The height you return on the example is not correct")
    public void testExampleMountain(){

        int[] example = new int[]{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1};

        int[] result = Valley.valley(example);

        assertEquals(3, result[1]);

    }

    @Test()
    @Grade(value=2, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "Your mountain height is not correct on a big array")
    public void testHeightRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertEquals(answer[1], result[1]);

    }

    @Test()
    @Grade(value=2, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "Your valley depth is not correct on a big array")
    public void testDepthRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertEquals(answer[0], result[0]);

    }

    @Test()
    @Grade(value=4, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "Your code is not working as it should on a big array")
    public void testRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertEquals(answer[0], result[0]);
        assertEquals(answer[1], result[1]);

    }
    
    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(onFail=true, message = "Your code is not working as it should on a big array")
    @GradeFeedback(onTimeout=true, message = "Your algorithm is too slow")
    public void testComplexity(){

        int[] example = buildArray(100000);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertEquals(answer[0], result[0]);
        assertEquals(answer[1], result[1]);

    }

    public static int[] correctValley(int[] array){

        int cmoun=0,mmoun=0,cval=0,mval=0;
        boolean up = true;
        if(array[0]<0)
            up=false;
        for(int i = 0 ; i < array.length; i++){
            if(up && array[i]>0){
                cmoun++;
            }else if(!up && array[i]<0){
                cval++;
            }else if(up && array[i]<0){
                up=false;
                mval = Math.max(Math.min(cval, cmoun), mval);
                cval = 1;
            }else{ //!up && array[i]>0
                up=true;
                mmoun = Math.max(Math.min(cval, cmoun), mmoun);
                cmoun=1;
            }
        }
        return new int[]{mval, mmoun};

    }

    public static int[] buildArray(int size){

        int[] array = new int[size];
        Random rng = new Random();

        for(int i = 0 ; i < size ; i++)
            array[i] = rng.nextInt(2)%2==0 ? 1 : -1;

        return array;
    }

}
