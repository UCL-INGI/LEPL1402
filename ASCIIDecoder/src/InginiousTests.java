package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {

    @Grade
    @Test
    public void basicTest(){
        String [][] input = new String[][]{{"072" ,"101", "108", "108", "111", "032", "119", "111", "114", "108", "100"}};
        String [] res = Decoder.decode(null, input);
        assertEquals(1, res.length);
        assertEquals(res[0], "Hello world");
    }


    @Grade
    @Test
    @GradeFeedback(onFail = true, message = "Your should never return null / your array should never contain null")
    public void npeTest(){
        String [] [] input = new String[][]{{}};
        String [] res = Decoder.decode(null, input);
        assertEquals(1, res.length);
        assertNotNull(res);
        assertNotNull(res[0]);
        assertEquals(0, res[0].length());
    }


    @Grade
    @Test
    public void biggerTest(){
        String [] [] input = new String[][]{{"084", "104", "105", "115", "032", "105", "115", "032"},
                                            {"097", "032", "108", "097", "114", "103", "101", "114"},
                                            {"115", "101", "116", "032", "111", "102", "032", "115", "101", "110",
                                                    "116", "101", "110", "099", "101", "115", "046", "046", "046"}};
        String [] res = Decoder.decode(null, input);
        assertEquals(3, res.length);
        assertEquals(res[0], "This is ");
        assertEquals(res[1], "a larger");
        assertEquals(res[2], "set of sentences...");
    }


    @Grade
    @Test
    @GradeFeedback(onFail = true, message = "Your code does not handle forbidden character correctly")
    public void forbiddenCharsTest(){
        String [] [] input = new String[][]{{"084", "104", "105", "115", "032", "105", "115", "032"},
                {"097", "032", "108", "097", "114", "103", "101", "114"},
                {"115", "101", "116", "032", "111", "102", "032", "115", "101", "110",
                        "116", "101", "110", "099", "101", "115", "046", "046", "046"}};
        String [] res = Decoder.decode(new int[]{32, 101}, input);
        assertEquals(3, res.length);
        assertEquals(res[0], "Thisis");
        assertEquals(res[1], "alargr");
        assertEquals(res[2], "stofsntncs...");
    }


    @Grade
    @Test
    @GradeFeedback(onFail = true, message = "What should your code return when a sentence is exclusively " +
            "made of forbidden characters ?")
    public void forbiddenOnlyTest(){
        String [] [] input = new String[][]{{"097", "098", "099", "100", "101", "102", "103"},
                                            {"097", "098", "099", "100", "101", "102", "103"},
                                            {"097", "098", "099", "100", "101", "102", "104"}};
        String [] res = Decoder.decode(new int[]{97, 98, 99, 100, 101, 102, 103}, input);
        assertEquals(3, res.length);
        assertNotNull(res[0]);
        assertNotNull(res[1]);
        assertNotNull(res[2]);
        assertEquals(res[0], "");
        assertEquals(res[1], "");
        assertEquals(res[2], "h");
    }

}
