package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {
    
    private Supplier<Integer> rng = () -> (int) (Math.random()*1000) + 10;
    private Method[] methods = IntroductionExercises.class.getMethods();
    
    private void checkSignature(String s, Class returnType) throws CustomGradingResult {
        boolean b = false;
        Method method = null;
        for(Method m : methods){
            if(m.getName().equals(s)) {
                b = true;
                method = m;
            }
        }
        try{
            if(!method.getReturnType().equals(returnType)){
                throw new CustomGradingResult(TestStatus.FAILED, 0, "The return type is not the right");
            }
            if(!Modifier.isPublic(method.getModifiers()) || !Modifier.isStatic(method.getModifiers())){
                throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method must be public and static");
            }
        } catch(NullPointerException e){
			throw new CustomGradingResult(TestStatus.FAILED, 0, "The method " + s + " doesn't exist");
        }
    }

    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testAttribute() throws CustomGradingResult {
		checkSignature("attribute", void.class);

        int a = rng.get();
        IntroductionExercises.attribute(a);
        if(IntroductionExercises.variable != a){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testAdd() throws CustomGradingResult {
		checkSignature("add", int.class);

        int a = rng.get();
        int b = rng.get();
        if((a+b) != IntroductionExercises.add(a, b)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testEqualsIntegers() throws CustomGradingResult {
		checkSignature("equalsIntegers", boolean.class);

        int a = rng.get();
        int b = rng.get();

        while(b == a){
            b = rng.get();
        }
        if(IntroductionExercises.equalsIntegers(a, b)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        if(!IntroductionExercises.equalsIntegers(a, a)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
        
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testMax() throws CustomGradingResult {
		int a = rng.get();
        int b = rng.get();
        if(((a > b) ? a : b) != IntroductionExercises.max(a,b)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testMiddleValue() throws CustomGradingResult {
		checkSignature("middleValue", int.class);

        int a = rng.get();
        int b = a + rng.get();
        int c = b + rng.get();

        if(b != IntroductionExercises.middleValue(a,b,c)
                || b != IntroductionExercises.middleValue(b, a, c)
                || b != IntroductionExercises.middleValue(c, a, b)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        if(-1 != IntroductionExercises.middleValue(a, a, b)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testGreetings() throws CustomGradingResult {
		checkSignature("greetings", String.class);

        if(!"Good morning, sir!".equals(IntroductionExercises.greetings("Morning")) ||
        !"Good evening, sir!".equals(IntroductionExercises.greetings("Evening")) ||
        !"Hello, sir!".equals(IntroductionExercises.greetings("morning")) ||
        !"Hello, sir!".equals(IntroductionExercises.greetings("evening"))){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testLastFirstMiddle() throws CustomGradingResult {
		checkSignature("lastFirstMiddle", int[].class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();

        int[] res = IntroductionExercises.lastFirstMiddle(array);

        if(res[0] != array[array.length-1] || res[1] != array[0] || res.length != 3
                || res[2] != array[array.length/2]){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testMaxArray() throws CustomGradingResult {
		checkSignature("maxArray", int.class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();
        int sum = 0;
        for(int i : array){
            sum += i;
        }

        if(sum != IntroductionExercises.sum(array)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testSum() throws CustomGradingResult {
		checkSignature("sum", int.class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();

        int max = array[0];
        for(int i : array){
            max = max > i ? max : i;
        }

        if(max != IntroductionExercises.maxArray(array)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    
    @Test
    @Grade(value = 1, custom = true, cpuTimeout = 3000)
    public void testMain() throws CustomGradingResult {
		int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();
        String[] args = new String[array.length];
        for(int i = 0; i<args.length; i++){
            args[i] = String.valueOf(array[i]);
        }
        try{
        	IntroductionExercises.main2(args);
        } catch(NullPointerException e){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Don't forget to initialize the variable squares");
        }

        int[] sq = Arrays.stream(array).map(i -> (int) Math.pow(i, 2)).toArray();

        if(!Arrays.equals(sq, IntroductionExercises.squares)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }

        String[] args2 = {"2", "1", "I'm really good", "10"};
        int[] res = {4, 1, 0, 100};

        IntroductionExercises.main2(args2);

        if(!Arrays.equals(res, IntroductionExercises.squares)){
            throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method does not work as espected");
        }
        throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
}
