package module4;

import org.junit.Test;

public class CoverageTestingTest
{
    @Test
    public void test() {
        // parameters for parseExp
        char[] in = new char[]{/*Some chars here*/};
        // you can also use .toCharArray from String class for this:
        // char[] in = myString.toCharArray();

        int offset = 0/*Some value HERE*/;
        int len = 0/*Some value HERE*/;
        // run the program with the given situation
        BigDecimal.parseExp(in, offset, len);
    }

    //TODO: Add more tests here.
}
