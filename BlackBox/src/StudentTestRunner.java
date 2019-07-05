package src;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.lang.reflect.Field;

public class StudentTestRunner {


    public static void main(String[] args) {

        // Give all the classes implementing binarySearch as arguments.

        boolean flag = false;
        FlavourExercise ex = new Exercise();
        int nDefinitions = 0;

        try {
            Field flavours = ex.getClass().getField("nImplems");
            nDefinitions = flavours.getInt(ex);
        } catch(NoSuchFieldException | IllegalAccessException e){
            System.err.println(e.getMessage());
            System.exit(1); // No point on continuing if there's nothing to run the student tests on
        }


        // The number of different implementations of binarySearch you want to submit the student test suite to.
        JUnitCore runner = new JUnitCore();

        for(int i=0; i < nDefinitions; i++) {

            try {
                Field field = ex.getClass().getDeclaredField("count");
                field.setAccessible(true); // Be cautious when using that.
                field.setInt(ex, i);
                field.setAccessible(false);
            } catch (NoSuchFieldException | IllegalAccessException e){
                System.err.println(e.getClass());
                flag = true;
            }
            //Get the student's test class here !!!
            Result result = runner.run(StudentTests.class);
            boolean correctness = ex.correctness();
            flag |= !(result.wasSuccessful() == correctness);

        }

        // If everything went well (flag = false) we exit with code 0
        // If smthing went wrong, exit with 1.
        if(!flag) {
            System.exit(0);
        } else {
            System.exit(1);
        }

    }

}