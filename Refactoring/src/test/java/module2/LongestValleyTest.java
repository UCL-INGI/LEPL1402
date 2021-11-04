package module2;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

//BEGIN STRIP
/*
 * Most of the code of this test suite is taken (and adapted) from an exercise of LSINF1121 course.
 * Original authors : Pierre Schaus, John Aoga
 * Modified by : Henri Piron
 */
//END STRIP
public class LongestValleyTest {

    //{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1}

    @Test()
    public void testExampleValley(){

        int[] example = new int[]{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1};

        int[] result = Valley.valley(example);

        assertNotNull(result);
        assertEquals(5, result[0]);
    }

    @Test()
    public void testExampleMountain(){

        int[] example = new int[]{1, 1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1};

        int[] result = Valley.valley(example);

        assertNotNull(result);
        assertEquals(3, result[1]);
    }

    @Test()
    public void testHeightEqualsArraySize(){

        int[] example = new int[]{1,1,1,1,1,-1,-1,-1,-1,-1};

        int[] result = Valley.valley(example);

        assertNotNull(result);
        assertEquals(5, result[1]);
    }

    @Test()
    public void testDepthEqualsArraySize(){

        int[] example = new int[]{-1,-1,-1,-1,-1,1,1,1,1,1};

        int[] result = Valley.valley(example);

        assertNotNull(result);
        assertEquals(5, result[0]);
    }

    //BEGIN STRIP
    @Test()
    public void testHeightRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertNotNull(result);
        assertEquals(answer[1], result[1]);
    }

    @Test()
    public void testDepthRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertNotNull(result);
        assertEquals(answer[0], result[0]);
    }

    @Test()
    public void testRandomBig(){

        int[] example = buildArray(100);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertNotNull(result);
        assertEquals(answer[0], result[0]);
        assertEquals(answer[1], result[1]);

    }

    @Test()
    public void testComplexity(){

        int[] example = buildArray(100000);

        int[] result = Valley.valley(example);
        int[] answer = correctValley(example);

        assertNotNull(result);
        assertEquals(answer[0], result[0]);
        assertEquals(answer[1], result[1]);

    }

    public static int[] correctValley(int[] array){

        int cmoun=0,mmoun=0,cval=0,mval=0;
        boolean up = true;

        if(array[0]<0) up=false;

        for(int i = 0 ; i < array.length; i++){

            if(up && array[i]>0) cmoun++;
            else if(!up && array[i]<0) cval++;
            else if(up && array[i]<0){
                up=false;
                cval = 1;
            }else{ //!up && array[i]>0
                up=true;
                cmoun=1;
            }

            int cmin = Math.min(cmoun, cval);
            if(cmin> mval && up) mval = cmin;
            else if(cmin > mmoun && !up) mmoun = cmin;

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
    //END STRIP
}
