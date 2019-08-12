package src;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class StudentTestRunner {

    public static void main(String[] args) {
        JUnitCore runner = new JUnitCore();
        Result result = runner.run(StudentTests.class);
    }

}