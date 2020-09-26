package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;

import templates.*;

import java.lang.reflect.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    // generate random number
    private Supplier<Double> rng = () -> ((Math.random()*100) + 1); // never generate 0

    // Check that Shape is correctly implemented
    @Test
    @Grade(value=3, custom = true)
    public void checkShapeImplementation() throws CustomGradingResult {
        Class c = Shape.class;
        int modifier_of_class = c.getModifiers();
        
        // should be abstract and public
        boolean isAbstract = Modifier.isAbstract(modifier_of_class);
        boolean isPublic = Modifier.isPublic(modifier_of_class);
        String result = (!isPublic) ? "public" : "abstract";
        
        if (!isAbstract || !isPublic){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "Shape class is not " + result);
        }

        // Now check if they respected given implementation ; will throw exception if not the case
        boolean success = true;
        double currentScore = 1;
        String[] methodsNames = new String[] {"getArea", "getPerimeter"};
        String error = "";
        try {

            for(int i = 0; success && i < methodsNames.length; i++) {
                Method my_method = c.getDeclaredMethod(methodsNames[i], double.class);
                int modifier_of_method = my_method.getModifiers();

                if (!Modifier.isAbstract(modifier_of_method)) {
                    success = false;
                    error = "Shape - method " + methodsNames[i] + " is not abstract";
                }

                if (success && !Modifier.isPublic(modifier_of_method)) {
                    success = false;
                    error = "Shape - method " + methodsNames[i] + " is not public";
                }

                if (success && !my_method.getReturnType().equals(double.class)) {
                    success = false;
                    error = "Shape - method " + methodsNames[i] + " does not have the correct return type";
                }

                if (success) {
                    currentScore = currentScore + 1;
                }
            }

        } catch (Exception e) {
            throw new CustomGradingResult(TestStatus.FAILED, 0, "You have probably forget to define getArea and/or getPerimeter methods");
        }

        if (success) {
            throw new CustomGradingResult(TestStatus.SUCCESS, 3);
        } else {
            throw new CustomGradingResult(TestStatus.FAILED, currentScore, error);
        }

    }

    @Test
    @Grade(value=2, custom = true)
    public void checkSubClassImplementation() throws CustomGradingResult {
        Class[] class_array = new Class[] {Circle.class, Square.class};
        String[] methodsNames = new String[] {"getArea", "getPerimeter"};
        boolean success = true;
        String error = "";
        double currentScore = 0;

        for(int i = 0; success && i < class_array.length; i++) {
            int modifier_of_class = class_array[i].getModifiers();
            // should be public only
            if (!(modifier_of_class == Modifier.PUBLIC)) {
                success = false;
                error = class_array[i].getSimpleName() + " is not public";
            }

            // should extends the Shape class
            if (success && !Shape.class.isAssignableFrom(class_array[i])) {
                success = false;
                error = class_array[i].getSimpleName() + " does not extend the Shape class";
            }

            try {
                
                // verify if methods are PUBLIC and have good RETURN type
                for(int j = 0 ; success && j < methodsNames.length; j++) {

                    Method my_method = class_array[i].getDeclaredMethod(methodsNames[j], new Class[]{double.class});
                    int modifier_of_method = my_method.getModifiers();

                    // should be only public
                    if (success && !(modifier_of_method == Modifier.PUBLIC)) {
                        success = false;
                        error = class_array[i].getSimpleName() + " - method " + methodsNames[i] + " is not public";
                    }

                    // should have correct return type
                    if (success && !(my_method.getReturnType().equals(double.class))) {
                        success = false;
                        error = class_array[i].getSimpleName() + " - method " + methodsNames[i] + " does not have the correct return type";
                    }
                }
                
                if (success) {
                    currentScore = currentScore + 1;
                }

            } catch (Exception e) {
                throw new CustomGradingResult(TestStatus.FAILED, 0, "You have probably forget to define getArea and/or getPerimeter methods inside a class");
            }

        }

        if (success) {
            throw new CustomGradingResult(TestStatus.SUCCESS, 2);
        } else {
            throw new CustomGradingResult(TestStatus.FAILED, currentScore, error);
        }

    }

    @Test
    @Grade(value=1, cpuTimeout=100)
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Not the expected result for getArea / getPerimeter\n", onFail = true, onTimeout = true)})
    public void testImplementationCorrectness() {
        Circle c = new Circle();
        Square s = new Square();

        for(int i = 0; i < 10; i++) {
            double random_double = rng.get();
            assertEquals(random_double * random_double * Math.PI, c.getArea(random_double), Math.pow(10, -10));
            assertEquals(random_double * random_double, s.getArea(random_double), Math.pow(10, -10));
            assertEquals(Math.PI * 2 * random_double, c.getPerimeter(random_double), Math.pow(10, -10));
            assertEquals(random_double * 4, s.getPerimeter(random_double), Math.pow(10, -10));
        }
    }

}
