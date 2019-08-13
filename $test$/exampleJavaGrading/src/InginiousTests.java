package src;

import com.github.guillaumederval.javagrading.GradeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import templates.*;

import static org.junit.Assert.*;

@RunWith(JailRunner.class)
@GradeClass(totalValue = 1, defaultCpuTimeout = 2000)
public class InginiousTests {

    @Test
    public void test1(){
        int x = SomeClass.someMethod(true);
        assertEquals(1, x);
    }

    @Test
    public void test2(){
        int x = SomeClass.someMethod(false);
        assertEquals(0, x);
    }

}
