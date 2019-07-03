import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;

import java.lang.reflect.InvocationTargetException;
import java.lang.AssertionError;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Runner {

    public static Class[] getClasses(String[] args) {
        Class [] classes = new Class[args.length];
        for(int index = 1 ; index < args.length; index++){
            try {
                classes[index] = Class.forName(args[index]);
                index++;
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
        return classes;
    }

    public static void main(String[] args) {
        Class [] classes = getClasses(args);
        // where we keep the result
        String filepath = args[0];

        //
        try (PrintWriter writer = new PrintWriter(new File(filepath))) {
            // run tests
            Result result = JUnitCore.runClasses(classes);

            if (result.wasSuccessful()) {
                System.exit(0);
            }

            for (Failure failure : result.getFailures()) {

                Class failureClass = failure.getException().getClass();
                // selon le type d'erreur, on peut générer un message de feedback pour l'étudiant
                Class [] knownFailure = { InvocationTargetException.class , AssertionError.class };
                boolean check = Arrays.stream(knownFailure).anyMatch(failureClass::equals);

                // known failure
                if (check) {

                    // cela suit la même convention
                    String [] desc = failure.toString().split("\\n");
                    String where = desc[0];
                    String classe = where.split("_")[0];
                    classe = Character.toUpperCase(classe.charAt(0)) + classe.substring(1);
                    String method = where.split("_")[1].split("\\(")[0];

                    // kind of failure
                    String kind = (failureClass == AssertionError.class ) ? "F" : "E" ;

                    // write to file
                    String msg = String.format("%s:%s:%s", kind, classe, method);
                    writer.println(msg);
                } else {

                    // generic behaviour : write as if
                    writer.println(failure.toString());
                }
            }
        } catch (Exception err) {
            err.printStackTrace();
            System.exit(42);
        }

    }

}