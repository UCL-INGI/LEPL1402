package module1;

import org.junit.Test;

import java.util.ConcurrentModificationException;

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
