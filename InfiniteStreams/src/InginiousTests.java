package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.Optional;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

  private Supplier<Integer> rng = () -> (int) ((Math.random()*100) + 1);
  private Supplier<Integer> rng2 = () -> (int) ((Math.random()*5) + 1);

  private IStream<Integer> generate(int from){
    return new Cons<>(from, () -> generate(from+1));
  }

  private IStream<Integer> prime(){
    return sieve(generate(2));
  }

  public IStream<Integer> sieve(IStream<Integer> s){
    return new Cons<>(s.head(), () -> sieve(s.tail().filter(n -> n % s.head() != 0)));
  }

  public IStream<Integer> goodFibonacci(){
        return new Cons<>(0, () -> new Cons<>(1, () -> nextFibPair(0, 1)));
    }

  public IStream<Integer> nextFibPair(int a, int b){
      int fib = a +b, prev = b;
      return new Cons<>(fib, () -> nextFibPair(prev, fib));
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "createWhile does not work\n", onFail = true, onTimeout = true)
  public void testCorrectnessCreateWhile(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> test = generate(rn).createWhile(n -> n <= rn + rn2);
    for(int i = rn; i<=rn+rn2; i++){
      assertTrue(test.head().equals(i));
      test = test.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1, custom = true)
  public void testCorrectness2CreateWhile() throws CustomGradingResult {
    int rn = rng.get();
    IStream<Integer> test = generate(rn).createWhile(n -> n <= rn + 1);
    try{
      test.tail().tail().head();
    } catch(NoSuchElementException e){
      throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method return an infinite stream");
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "map does not work\n", onFail = true, onTimeout = true)
  public void testMap(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> stream = generate(rn).helper(n -> n <= rn+rn2);
    IStream<Integer> test = stream.map(i -> i+1);
    for(int i = rn; i<=rn+rn2; i++){
      assertTrue(test.head().equals(i+1));
      test = test.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "filter does not work\n", onFail = true, onTimeout = true)
  public void testFilter(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> stream = generate(rn);
    IStream<Integer> test = stream.filter(i -> i % 2 == 0);
    for(int i = rn; i<=(rn+rn2); i++){
      if(i%2 == 0){
        assertTrue(test.head().equals(i));
        test = test.tail();
      }

    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1, custom=true)
  public void testTypeOfInstanceVariables() throws CustomGradingResult {
    try{
        if(Cons.class.getDeclaredField("tail").getType().equals(Supplier.class)){
          throw new CustomGradingResult(TestStatus.SUCCESS, 1, "You've used a Supplier for the tail");
        }
      } catch (NoSuchFieldException e){
          throw new CustomGradingResult(TestStatus.FAILED, 0, "Try to use a Supplier");
      }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "fromInteger does not work\n", onFail = true, onTimeout = true)
  public void testFromInteger(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> test = PlayWithStreams.fromInteger(rn);
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals(i));
      test = test.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "boolean from modulo does not work\n", onFail = true, onTimeout = true)
  public void testBooleanFromModulo(){
    int rn = rng.get();
    int rn2 = rng.get();
    int a = rng2.get();
    int b = rng2.get();
    IStream<Boolean> test = PlayWithStreams.booleanFromModulo(a, b);
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals((a%b == 0)));
      test = test.tail();
      a+=2;
      b+=1;
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "finiteStream does not work\n", onFail = true, onTimeout = true)
  public void testFiniteStream(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> test = PlayWithStreams.finiteStream(rn, rn+rn2);
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals(i));
      test = test.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1, custom = true)
  public void testFiniteStreamLength() throws CustomGradingResult {
    int rn = rng.get();
    IStream<Integer> test = PlayWithStreams.finiteStream(rn, rn+1);
    try{
      test.tail().tail().head();
    } catch(NoSuchElementException e){
      throw new CustomGradingResult(TestStatus.SUCCESS, 1);
    }
    throw new CustomGradingResult(TestStatus.FAILED, 0, "Your method return an infinite stream");
  }

  @Test
  @Grade(cpuTimeout=100, value = 1)
  @GradeFeedback(message = "primeStream and sieve does not work\n", onFail = true, onTimeout = true)
  public void testPrimeStream(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> test = PlayWithStreams.primeStream();
    IStream<Integer> good = prime();
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals(good.head()));
      test = test.tail();
      good = good.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1, custom=true)
  @GradeFeedback(message = "infiniteModulo does not work\n", onFail = true, onTimeout = true)
  public void testInfiniteModulo(){
    int rn = rng.get();
    int rn2 = rng.get();
    int rn3 = rng2.get();
    IStream<Integer> stream = generate(rn);
    IStream<Integer> test = PlayWithStreams.infiniteModulo(stream,rn3);
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals(i%rn3));
      test = test.tail();
    }
  }

  @Test
  @Grade(cpuTimeout=100, value = 1, custom=true)
  @GradeFeedback(message = "fibonacci does not work\n", onFail = true, onTimeout = true)
  public void testFibonacci(){
    int rn = rng.get();
    int rn2 = rng.get();
    IStream<Integer> test = PlayWithStreams.fibonacci();
    IStream<Integer> good = goodFibonacci();
    for(int i = rn; i<=(rn+rn2); i++){
      assertTrue(test.head().equals(good.head()));
      test = test.tail();
      good = good.tail();
    }
  }
}
