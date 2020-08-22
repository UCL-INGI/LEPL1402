package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class)
public class InginiousTests {
    public static final int STATUS_KO = 1;
    public static final int STATUS_TOO_MANY_SWAPS = 2;
    public static final int STATUS_TOO_MANY_POPS = 4;
    public static final int STATUS_EXCEPTION = 8;
    public int run(int[] array) {
        LinkedList l = new LinkedList(array);

        try {
            Sorter.sort(l);
        }
        catch (Throwable e) {
            return STATUS_EXCEPTION | STATUS_KO;
        }

        int pops = l.getnPops();
        int swaps = l.getnSwaps();
        int out = 0;
        if(!l.isSorted())
            out |= STATUS_KO;
        if(pops > array.length*array.length)
            out |= STATUS_TOO_MANY_POPS;
        if(swaps > array.length*array.length)
            out |= STATUS_TOO_MANY_SWAPS;

        return out;
    }

    public int grade(int status) {
        int grade = 0; //will be on 4
        if(!((status & STATUS_KO) == STATUS_KO)) {
            grade = 2;
            if (!((status & STATUS_TOO_MANY_POPS) == STATUS_TOO_MANY_POPS))
                grade += 1;
            if (!((status & STATUS_TOO_MANY_SWAPS) == STATUS_TOO_MANY_SWAPS))
                grade += 1;
        }
        return grade;
    }

    public void runAll(int[][] arrays) throws CustomGradingResult {
        int statusAll = 0;
        int gradeAll = 0;
        for(int[] i: arrays) {
            int status = run(i);
            statusAll |= status;
            gradeAll += grade(status);
        }

        String feedback = "";
        TestStatus status = TestStatus.SUCCESS;
        if(statusAll != 0) {
            if((statusAll & STATUS_KO) == STATUS_KO) {
                feedback += "Some test cases were incorrectly sorted. ";
                status = TestStatus.FAILED;
            }
            if((statusAll & STATUS_EXCEPTION) == STATUS_EXCEPTION) {
                feedback += "Your code sometimes throws exceptions when it shouldn't. ";
                status = TestStatus.FAILED;
            }
            if((statusAll & STATUS_TOO_MANY_POPS) == STATUS_TOO_MANY_POPS) {
                feedback += "Your code makes too many calls to `pop`. ";
                if(status == TestStatus.SUCCESS)
                    status = TestStatus.TIMEOUT;
            }
            if((statusAll & STATUS_TOO_MANY_SWAPS) == STATUS_TOO_MANY_SWAPS) {
                feedback += "Your code makes too many calls to `swap`. ";
                if(status == TestStatus.SUCCESS)
                    status = TestStatus.TIMEOUT;
            }
        }
        throw new CustomGradingResult(status, gradeAll, feedback);
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void alreadySorted() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for(int i = 0; i < 25; i++) {
            arrays[i][0] = r.nextInt() % 10000;
            for(int j = 1; j < 100; j++)
                arrays[i][j] = arrays[i][j-1] + Math.abs((r.nextInt() % 10000));
        }

        runAll(arrays);
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void reverseSorted() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for(int i = 0; i < 25; i++) {
            arrays[i][99] = r.nextInt() % 10000;
            for(int j = 98; j >= 0; j--)
                arrays[i][j] = arrays[i][j+1] + Math.abs((r.nextInt() % 10000));
        }

        runAll(arrays);
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void allSortedButOne() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for(int i = 0; i < 25; i++) {
            arrays[i][0] = r.nextInt() % 10000;
            for(int j = 1; j < 100; j++)
                arrays[i][j] = arrays[i][j-1] + Math.abs((r.nextInt() % 10000));

            int pos = r.nextInt(99);
            arrays[i][pos] = arrays[i][99] + Math.abs((r.nextInt() % 10000));
        }

        runAll(arrays);
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void twoDifferent() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for(int i = 0; i < 25; i++) {
            int a = r.nextInt(1000000);
            int b = r.nextInt(1000000);
            for(int j = 0; j < 100; j++)
                arrays[i][j] = r.nextBoolean() ? a : b;
        }

        runAll(arrays);
    }

    @Test
    @Grade(value = 100, custom = true, cpuTimeout = 1000)
    public void bimonotonous() throws CustomGradingResult {
        int[][] arrays = new int[25][100];

        Random r = new Random(582);

        for(int i = 0; i < 25; i++) {
            int midPos = r.nextInt(98) + 1;
            boolean direction = r.nextBoolean();
            int multiplier = direction ? -1 : 1;

            arrays[i][midPos] = r.nextInt(10000);
            for(int j = midPos-1; j >= 0; j--)
                arrays[i][j] = arrays[i][j+1] + multiplier * r.nextInt(10000);
            for(int j = midPos+1; j < 100; j++)
                arrays[i][j] = arrays[i][j-1] + multiplier * r.nextInt(10000);
        }

        runAll(arrays);
    }

    @Test
    @Grade(value = 500, custom = true, cpuTimeout = 10000)
    public void random() throws CustomGradingResult {
        int[][] arrays = new int[125][100];

        Random r = new Random(582);

        for(int i = 0; i < 125; i++) {
            for(int j = 0; j < 100; j++)
                arrays[i][j] = r.nextInt();
        }

        runAll(arrays);
    }
}
