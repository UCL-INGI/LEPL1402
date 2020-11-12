package src;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;

import templates.*;

public class StudentTestRunner {


    public static void main(String[] args) {

        Exercise ex = new Exercise();

        boolean hasErrors = false;
        int falsePositive = 0;
        int falseNegative = 0;
        int bugsFound = 0;

        JUnitCore runner = new JUnitCore();

        List<String> bugsDetected = new ArrayList<>();

        for (int i = 0; i < ex.nbImplem(); i++) {
            ex.count = i;
            Result result = runner.run(StudentTests.class);
            List<Failure> failures = result.getFailures();

            for(Failure fail : failures){
                if(fail.getMessage() != null && fail.getMessage().contains("access denied")){
                    // If the student uses a forbidden instruction, exit with this code 2
                    // The run script SHOULD interpret a Sys exit 2 as a permission error
                    // And therefore inform the student that he/she used some forbidden instruction.
                    System.exit(2);
                }
                if(fail.getMessage() != null && fail.getMessage().contains("No runnable methods")){
                    System.out.println("NO RUNNABLE METHODS");
                    System.exit(3);
                }
            }

            boolean shouldBeCorrect = ex.correctness();
            if (!result.wasSuccessful() && !shouldBeCorrect) {
                bugsFound ++;
                bugsDetected.add(ex.feedbackMessage());
            }

            if (!result.wasSuccessful() && shouldBeCorrect) {
                hasErrors = true;
                falsePositive += 1;
            }

            if (result.wasSuccessful() && !shouldBeCorrect) {
                hasErrors = true;
                falseNegative += 1;
            }


        }

        System.out.println(bugsFound+"\t"+ex.nBugs+"\t"+(falsePositive > 0 ? "FP" : "NFP")); 

        System.out.println(String.format("You found %d bugs out of %d and detected %d bugs in correct code. You detected the following bugs:", bugsFound, ex.nBugs, falsePositive));
        for (String s : bugsDetected) {
            System.out.println(s);
        }

        // If everything went well (flag = false) we exit with code 0
        // If smthing went wrong, exit with 1.
        if(!hasErrors) {
            System.exit(0);
        } else {
            System.exit(1);
        }

    }

}
