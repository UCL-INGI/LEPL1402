package src;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.util.List;

import templates.*;

public class StudentTestRunner {


    public static void main(String[] args) {

        // Give all the classes implementing binarySearch as arguments.

        boolean flag = false;
        boolean fp = false; // if the student test suite yields false positive
        ExerciseFlavour ex = new Exercise();
        int nDefinitions = 0;
        int nBugs = 0;
        int nBugsFound = 0;
        String feedbackMessage = "";

        try {
            //The number of different implementations of binarySearch you want to submit the student test suite to.
            Field flavours = ex.getClass().getField("nImplems");
            nDefinitions = flavours.getInt(ex);
            Field bugs = ex.getClass().getField("nBugs");
            nBugs = bugs.getInt(ex);
        } catch(NoSuchFieldException | IllegalAccessException e){
            System.err.println(e.getMessage());
            System.exit(1); // No point on continuing if there's nothing to run the student tests on
        }



        JUnitCore runner = new JUnitCore();

        for(int i=0; i < nDefinitions; i++) {

            try {
                Field field = ex.getClass().getDeclaredField("count");
                field.setAccessible(true); // BE CAUTIOUS WHEN USING SUCH INSTRUCTIONS
                field.setInt(ex, i);
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e){
                System.err.println(e.getClass());
                flag = true;
            }

            //Get the student's test class here !!!
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

            boolean correctness = ex.correctness();
            flag |= !(result.wasSuccessful() == correctness);
            // If the flavour is supposed to be correct and the test suite is successful, then OK.
            // If the flavour has a bug and the students' suite fails, then OK.
            // Else, KO.

            if(!result.wasSuccessful() && !correctness) {
                nBugsFound++; // the std correctly found a bug
                feedbackMessage+=Exercise.feedbackMessages[i]+",";
            }
            if(!result.wasSuccessful() && correctness) {
                fp = true; // the std code yield a false positive
                feedbackMessage+=i;
                feedbackMessage+=Exercise.feedbackMessages[i]+",";
            }

        }

        System.out.println(nBugsFound+"\t"+nBugs+"\t"+(fp ? "FP" : "NFP"));
        System.out.println(feedbackMessage);
        // NBUGSFOUND   NBUGS   FP or NFP

        // If everything went well (flag = false) we exit with code 0
        // If smthing went wrong, exit with 1.
        if(!flag) {
            System.exit(0);
        } else {
            System.exit(1);
        }

    }

}