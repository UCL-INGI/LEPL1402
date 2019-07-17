package templates;

import com.github.guillaumederval.javagrading.GradeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import src.JailRunner;
import src.Exercise;
import static org.junit.Assert.*;

// CAREFUL WHEN YOU SET A defaultCpuTimeout WITH INGINIOUS, ALWAYS SET IT LOWER THAN WHAT
// THE VALUE YOU CHOSE FOR YOUR OWN MACHINE

@RunWith(JailRunner.class)
@GradeClass(totalValue = 1, defaultCpuTimeout = 500)
public class StudentTests {

    // STUDENT CODE WILL BE INSERTED HERE
@   @student_code@@
    // END OF STUDENT CODE

}
