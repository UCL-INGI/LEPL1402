package src;

import com.github.guillaumederval.javagrading.GradingListener;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;

public class StudentTestRunner {

    public static void main(String [] args) {

        JUnitCore runner = new JUnitCore();
        runner.addListener(new GradingListener());
        Result result = runner.run(InginiousTests.class);

        if(result.wasSuccessful()){
            System.exit(0);
        } else {
            determineProblem(result);
        }
    }

    /*
     * Makes the distinction between two different failure cases :
     *  - The student has a mistake in his/her code. (Sys exit 1)
     *  - The student used a forbidden instruction in his/her code. (Sys exit 2)
     */
    private static void determineProblem(Result result){
        boolean flag = false;

        if(result.getFailureCount() != 0) {
            List<Failure> failures = result.getFailures();

            for (Failure fail : failures) {
                flag |= fail.getMessage()!= null && fail.getMessage().contains("access denied");
            }
        }

        if(flag) {
            System.exit(2); // The student used a forbidden instruction
        } else {
            System.exit(1); // There's an error in your code etc etc...
        }
    }

}