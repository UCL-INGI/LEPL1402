
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.lang.reflect.Field;
import java.util.List;


public class StudentTestRunner {


    private static Class [] getClass(String [] args) {
        Class [] c = new Class[args.length];
        for(int i=0;i<args.length;i++){
            try{
                c[i] = Class.forName("flavour."+args[i]);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return c;
    }



    public static void main(String[] args) {

        // Give all the classes implementing binarySearch as arguments.

        boolean flag = false;


        Class [] cls = getClass(args);
        int nDefinitions = cls.length;
        for(Class c : cls){
            System.out.println(c.getName());
        }
        // The number of different implementations of binarySearch you want to submit the student test suite to.

        for(int i=0; i<nDefinitions; i++) {

            StudentTests.count = i;

            //Get the student's test class here !!!
            Result result = JUnitCore.runClasses(StudentTests.class);

            try {
                Field field = cls[i].getField("correctness");
                boolean correctness = (boolean) field.get(null);
                flag = !(result.wasSuccessful() && correctness);
            } catch (IllegalAccessException | NoSuchFieldException e){
                System.err.println(e.getMessage());
            }

            List<Failure> fails = result.getFailures();
            // TODO: 3/07/19 - check if failures are the one expected
            for(Failure fail : fails){
                System.out.println(fail.getClass().getName());
            }

        }

        // If everything went well (flag = false) we exit with code 0
        // This exit code will be caught in the run file
        if(!flag){
            System.exit(0);
        }


    }

}