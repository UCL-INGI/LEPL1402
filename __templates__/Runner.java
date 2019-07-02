import java.util.List;
import java.util.ArrayList;

import java.lang.reflect.InvocationTargetException;
import java.lang.AssertionError;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Runner {

    public static Class[] getClasses(String[] args) {
        Class [] classes = new Class[args.length];
        int index = 1;
        for(String classString: args){
            try {
                classes[index] = Class.forName(args[index])
                index++;
            } catch () {
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

        // run tests
        Result result = JUnitCore.runClasses(classes);
        for (Failure failure : result.getFailures()) {

            Class failureClass = failure.getException().getClass();
            // selon le type d'erreur, on peut générer un message de feedback pour l'étudiant
            Class [] knownFailure = { InvocationTargetException.class , AssertionError.class }
            boolean result = Arrays.stream(knownFailure).anyMatch(failureClass::equals);

            // known failure
            if (result) {

                // cela suit la même convention
                String [] desc = failure.toString().split("\\n");
                String where = desc[0];
                String classe = where.split("_")[0];
                classe = Character.toUpperCase(classe.charAt(0)) + classe.substring(1);
                String method = where.split("_")[1].split("\\(")[0];

                // kind of failure

                // TODO écrire dans un fichier

            } else {

            }
        }

    }

}