package module1;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class IntroductionTest {
    
    private Supplier<Integer> rng = () -> (int) (Math.random()*1000) + 10;
    private Method[] methods = Introduction.class.getMethods();
    
    private void checkSignature(String s, Class returnType) {
        boolean b = false;
        Method method = null;
        for(Method m : methods){
            if(m.getName().equals(s)) {
                b = true;
                method = m;
            }
        }


        try{
            assertEquals("The return type is not the right", method.getReturnType(), returnType);
            assertTrue("Your method must be public and static", Modifier.isPublic(method.getModifiers()) || !Modifier.isStatic(method.getModifiers()));
        } catch(NullPointerException e){
            fail("The method " + s + " doesn't exist");
        }
    }

    @Test
    public void testAttribute() {
		checkSignature("attribute", void.class);

        int a = rng.get();
        Introduction.attribute(a);

        assertEquals("Your method does not work as espected", Introduction.variable, a);
    }
    
    @Test
    public void testAdd() {
		checkSignature("add", int.class);

        int a = rng.get();
        int b = rng.get();

        assertEquals("Your method does not work as espected", a+b, Introduction.add(a, b));
    }
    
    @Test
    public void testEqualsIntegers() {
		checkSignature("equalsIntegers", boolean.class);

        int a = rng.get();
        int b = rng.get();

        while(b == a){
            b = rng.get();
        }

        assertFalse("Your method does not work as espected", Introduction.equalsIntegers(a, b));
        assertTrue("Your method does not work as espected", Introduction.equalsIntegers(a, a));
    }
    
    @Test
    public void testMax() {
		int a = rng.get();
        int b = rng.get();

        assertEquals("Your method does not work as espected", ((a > b) ? a : b), Introduction.max(a,b));
    }
    
    @Test
    public void testMiddleValue() {
		checkSignature("middleValue", int.class);

        int a = rng.get();
        int b = a + rng.get();
        int c = b + rng.get();

        assertFalse("Your method does not work as espected", b != Introduction.middleValue(a,b,c)
                || b != Introduction.middleValue(b, a, c)
                || b != Introduction.middleValue(c, a, b));

        assertEquals("Your method does not work as espected", -1, Introduction.middleValue(a, a, b));
    }
    
    @Test
    public void testGreetings() {
		checkSignature("greetings", String.class);

        assertFalse("Your method does not work as espected", !"Good morning, sir!".equals(Introduction.greetings("Morning")) ||
                !"Good evening, sir!".equals(Introduction.greetings("Evening")) ||
                !"Hello, sir!".equals(Introduction.greetings("morning")) ||
                !"Hello, sir!".equals(Introduction.greetings("evening")));
    }
    
    @Test
    public void testLastFirstMiddle() {
		checkSignature("lastFirstMiddle", int[].class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();

        int[] res = Introduction.lastFirstMiddle(array);
        assertFalse("Your method does not work as espected", res[0] != array[array.length-1] || res[1] != array[0] || res.length != 3
                || res[2] != array[array.length/2]);
    }
    
    @Test
    public void testMaxArray() {
		checkSignature("maxArray", int.class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();
        int max = Integer.MIN_VALUE;
        for(int i : array){
            if (i > max) {
                max = i;
            }
        }

        assertEquals("Your method does not work as espected", max, Introduction.maxArray(array));
    }
    
    @Test
    public void testSum() {
		checkSignature("sum", int.class);

        int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();

        int sumi = 0;
        for(int i : array){
            sumi+= i;
        }

        assertEquals("Your method does not work as espected", sumi, Introduction.sum(array));
    }
    
    @Test
    public void testMain() {
		int[] array = Stream.generate(rng).limit(rng.get()).mapToInt(i -> i).toArray();
        String[] args = new String[array.length];
        for(int i = 0; i<args.length; i++){
            args[i] = String.valueOf(array[i]);
        }
        try{
        	Introduction.main2(args);
        } catch(NullPointerException e){
            fail("Don't forget to initialize the variable squares");
        }

        int[] sq = Arrays.stream(array).map(i -> (int) Math.pow(i, 2)).toArray();

        assertEquals("Your method does not work as espected", sq, Introduction.squares);

        String[] args2 = {"2", "1", "I'm really good", "10"};
        int[] res = {4, 1, 0, 100};

        Introduction.main2(args2);

        assertEquals("Your method does not work as espected", res, Introduction.squares);
    }
}
