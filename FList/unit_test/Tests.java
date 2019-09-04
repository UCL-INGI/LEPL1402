import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Random;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class Tests {

    private boolean flag = true;
    private Supplier<Integer> rng = () -> (int) (new Random().nextInt(100));

    private int[] randomNumberSup(int size){
        return Stream.generate(rng).limit(size).mapToInt(i->i).toArray();
    }
    private Object[] asArray(FList list){
        Object[] array = new Object[list.length()];
        int index= 0;
        for(Object o : list){
            array[index++] = o;
        }
        return array;
    }

    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message = "You failed the example", onFail = true, onTimeout = true)
    public void testExample(){

        FList<Integer> list = FList.nil();

        for (int i = 0; i < 10; i++) {
            list = list.cons(i);
        }
        int index = 10;
        list = list.map(i -> i+1);
        // will print 1,2,...,11
        for (Integer i: list) {
            int k = i.intValue();
            assertEquals(k, index);
            index--;
        }

        list = list.filter(i -> i%2 == 0);
        // will print 2,4,6,...,10
        index = 10;
        for (Integer i: list) {
            int k = i.intValue();
            assertEquals(k, index);
            index-=2;
        }
        flag = false;

    }

    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message="The length you return is not correct", onFail=true)
    public void testLength(){

        int[] sizes = randomNumberSup(100);

        for(int i = 0 ; i<100 ; i++){

            FList<Integer> list = FList.nil();

            for(int j = 0 ; j < sizes[i] ; j++){
                list = list.cons(new Random().nextInt(100));
            }

            assertEquals(list.length(), sizes[i]);
        }

        FList<Integer> list = FList.nil();
        assertEquals(list.length(), 0);

    }


    @Test()
    @Grade(value=10, custom=true, cpuTimeout=100)
    public void testIterator() throws CustomGradingResult{
        FList<Integer> list = FList.nil();

        int[] values = randomNumberSup(100);

        for(int i = 0 ; i < 100; i++){
            list = list.cons(values[i]);
        }
        int index= 99;
        for(Integer i : list){
            assertEquals(values[index--],i.intValue());
        }

        boolean noSuch = false, concurr=false;

        try{
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            it.next();
        }catch(NoSuchElementException e){
            noSuch = true;
        }

        try{
            FList<Integer> fl = FList.nil();
            Iterator it = fl.iterator();
            fl = fl.cons(1);
            it.remove();
        }catch(UnsupportedOperationException e){
            concurr = true;
        }

        if(!concurr && !noSuch){
            throw new CustomGradingResult(TestStatus.FAILED, 0 , "You forgot about the exceptions");
        }
        if(!concurr){
            throw new CustomGradingResult(TestStatus.FAILED, 3, "You forgot about the UnsupportedOperationException");
        }
        if(!noSuch){
            throw new CustomGradingResult(TestStatus.FAILED, 3 , "You forgot about the NoSuchElementException");
        }

    }


}
