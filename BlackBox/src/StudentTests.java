package src;


import com.github.guillaumederval.javagrading.GradeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(JailRunner.class)
@GradeClass(totalValue = 1, defaultCpuTimeout = 1000)
public class StudentTests {

    // STUDENT CODE WILL BE INSERTED HERE
    @Test
    public void test(){

        int [] arr = new int[] {1, 3, 5, 6, 8, 10, 11, 13, 17, 42, 100, 110, 121};
        int res = Exercise.binarySearch(arr, 0, arr.length-1, 10);
        assertEquals(5, res);

    }
    // END OF STUDENT CODE

}
