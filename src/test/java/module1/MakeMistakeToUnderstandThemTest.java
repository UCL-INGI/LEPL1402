package src;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

public class MakeMistakeToUnderstandThemTest {

    @Test(expected = ConcurrentModificationException.class)
    public void testConcurrentModificationException(){

        MakeMistakeToUnderstandThem.concurr();
        
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testArrayIndexOutOfBoundsException(){
        
        MakeMistakeToUnderstandThem.outof();

    }
    
    @Test(expected = NullPointerException.class)
    public void testNullPointerException(){
        
        MakeMistakeToUnderstandThem.nullpointer();

    }
    

}
