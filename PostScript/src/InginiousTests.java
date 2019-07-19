package src;


import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;

import org.junit.*;
import org.junit.runner.RunWith;
import java.util.EmptyStackException;

import templates.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {
    private InterpreterInterface i;
    
    @Before
    public void setUp(){
        i = new Interpreter();
    }
    
    @After
    public void tearDown(){
        i = null;
    }
    
    // MISC
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Fail with dup and mul together\n", onFail = true, onTimeout = true)})
    public void missionExampleAboutDupAndMul(){
        Assert.assertEquals(
                            "5.5 dup mul pstack must yield 30.25",
                            "30.25", i.interpret("5.5 dup mul pstack"));
        Assert.assertEquals("", i.interpret("pop"));
    }
    
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "true should be a valid token\n", onFail = true, onTimeout = true)})
    public void trueIsAValidToken(){
        Assert.assertEquals("true", i.interpret("true pstack"));
    }

    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "false should be a valid token\n", onFail = true, onTimeout = true)})
    public void falseIsAValidToken(){
        Assert.assertEquals("false", i.interpret("false pstack"));
    }
    
    //	Qq on decide ? toute division -> double ou semantique Java
    //	@Test
    //	public void testSlackEx(){
    //		Assert.assertEquals("2.5", i.interpret("5 2 div pstack"));
    //		Assert.assertEquals("5.0", i.interpret("2 mul pstack"));
    //	}
    
    // EXCH
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Exch doesn't work as expected\n", onFail = true, onTimeout = true)})
    public void testExch(){
        Assert.assertEquals(
                            "Exch swaps the top two items on the stack",
                            "3 2", i.interpret("2 3 exch pstack"));
    }
    
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Calling exch on an empty stack must throw an EmptyStackException\n", onFail = true, onTimeout = true)})
    public void testExchEmpty(){
        i.interpret("exch pstack");
        Assert.fail("Calling exch on an empty stack must throw an EmptyStackException");
    }
    
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Calling exch on a stack of length 1 must throw an EmptyStackException\n", onFail = true, onTimeout = true)})
    public void testExchNotEnoughOperands(){
        i.interpret("1 exch pstack");
        Assert.fail("Calling exch on a stack of length 1 must throw an EmptyStackException");
    }
    
    // SYMBOLS
    @Ignore
    @Test(expected=RuntimeException.class)
    public void testItThrowsAnExceptionWhenSymbolIsUsedBeforeDefinition(){
        i.interpret("one /one 1 def 1 add pstack");
        Assert.fail("An exception must be thrown when a symbol is used before being declared");
    }
    @Ignore
    @Test
    public void testItCanUseSymbols(){
        Assert.assertEquals(
                            "Symbols can be used for their value in expressions",
                            "2", i.interpret("/one 1 def one 1 add pstack"));
    }
    @Ignore
    @Test
    public void symbolDefinitionCanReferenceAnotherSymbol(){
        Assert.assertEquals(
                            "A symbol definition can reference another symbol",
                            "2", i.interpret("/a 1 def /b a def a b add pstack"));
    }
    @Ignore
    @Test
    public void ignoreOperatorOverriding(){
        Assert.assertEquals(
                            "When a symbol redefines a keyword, it is ignored",
                            "4", i.interpret("/add 1 def 2 2 add pstack"));
    }
    @Ignore
    @Test
    public void testIncompleteDef(){
        Assert.assertEquals(
                            "Is must be possible to print incomplete partial definition with pstack",
                            "/aa 2 /bb 3", i.interpret("/aa 2 /bb 3 pstack"));
    }
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Interpreting the empty string must yield the empty string\n", onFail = true, onTimeout = true)})
    public void interpretEmptyString(){
        Assert.assertEquals(
                            "Interpreting the empty string must yield the empty string",
                            "", i.interpret(""));
    }
    
    // ADD
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding two integers yields the integer sum of the terms\n", onFail = true, onTimeout = true)})
    public void addingTwoIntegersMustYieldTheIntegerSum(){
        Assert.assertEquals(
                            "Adding two integers yields the integer sum of the terms",
                            "2", i.interpret("1 1 add pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding two integers yields the integer sum of the terms\n", onFail = true, onTimeout = true)})
    public void addTwoOperands(){
        Assert.assertEquals(
                            "Adding two integers yields the integer sum of the terms",
                            "5", i.interpret("2 3 add pstack"));
    }
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding two negative integers yields their (negative) integer sum\n", onFail = true, onTimeout = true)})
    public void addNegativeIntOperands(){
        Assert.assertEquals(
                            "Adding two negative integers yields their (negative) integer sum",
                            "-5", i.interpret("-2 -3 add pstack"));
    }
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding an integer and a floating point value yields a floating point value\n", onFail = true, onTimeout = true)})
    public void addIntAndDoubleOperands(){
        Assert.assertEquals(
                            "Adding an integer and a floating point value yields a floating point value",
                            "5.0", i.interpret("2 3.0 add pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding an a floating point value and an integer yields a floating point value\n", onFail = true, onTimeout = true)})
    public void addDoubleAndIntOperands(){
        Assert.assertEquals(
                            "Adding an a floating point value and an integer yields a floating point value",
                            "5.0", i.interpret("2.0 3 add pstack"));
    }
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Adding two negative floating point value returns their (negative) floating point sum\n", onFail = true, onTimeout = true)})
    public void addNegativeDoubleOperands(){
        Assert.assertEquals(
                            "Adding two negative floating point value returns their (negative) floating point sum",
                            "-5.0", i.interpret("-2.0 -3 add pstack"));
    }
    
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Calling add when there is only one item on the stack must throw an EmptyStackException\n", onFail = true, onTimeout = true)})
    public void addOneOperandThrowsException(){
        i.interpret("-3 add ");
        Assert.fail("Calling add when there is only one item on the stack must throw an EmptyStackException");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Boolean don't support addition\n", onFail = true, onTimeout = true)})
    public void boolDontSupportAddition(){
        i.interpret("true false add");
        Assert.fail("Boolean don't support addition");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Boolean don't support subtraction\n", onFail = true, onTimeout = true)})
    public void boolDontSupportSubtraction(){
        i.interpret("true false sub");
        Assert.fail("Boolean don't support subtraction");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Boolean don't support multiplication\n", onFail = true, onTimeout = true)})
    public void boolDontSupportMultiplication(){
        i.interpret("true false mul");
        Assert.fail("Boolean don't support multiplication");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Boolean don't support division\n", onFail = true, onTimeout = true)})
    public void boolDontSupportDivision(){
        i.interpret("true false div");
        Assert.fail("Boolean don't support division");
    }
    
    // SUB
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Subtracting two integers can yield a negative integer\n", onFail = true, onTimeout = true)})
    public void subTwoOperands(){
        // 2 3 sub == 2 - 3
        Assert.assertEquals(
                            "Subtracting two integers can yield a negative integer",
                            "-1", i.interpret("2 3 sub pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It must be possible to subtract a negative integer\n", onFail = true, onTimeout = true)})
    public void subNegativeIntOperand(){
        Assert.assertEquals(
                            "It must be possible to subtract a negative integer",
                            "-5", i.interpret("-2 3 sub pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It must be possible to subtract an int and a float value\n", onFail = true, onTimeout = true)})
    public void subIntDoubleOperand(){
        Assert.assertEquals(
                            "It must be possible to subtract an int and a float value",
                            "-5.0", i.interpret("-2 3.0 sub pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It must be possible to subtract a float value and an int\n", onFail = true, onTimeout = true)})
    public void subDoubleIntOperand(){
        Assert.assertEquals(
                            "It must be possible to subtract a float value and an int",
                            "-5.0", i.interpret("-2.0 3 sub pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It must be possible to subtract a negative float value\n", onFail = true, onTimeout = true)})
    public void subNegativeDoubleOperand(){
        Assert.assertEquals(
                            "It must be possible to subtract a negative float value",
                            "-5.0", i.interpret("-2.0 3 sub pstack"));
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Subtraction requires two operands on the stack\n", onFail = true, onTimeout = true)})
    public void subOneOperand(){
        i.interpret("3 sub pstack");
        Assert.fail("Subtraction requires two operands on the stack");
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Subtraction requires two operands on the stack\n", onFail = true, onTimeout = true)})
    public void subEmpty(){
        i.interpret("sub pstack");
        Assert.fail("Subtraction requires two operands on the stack");
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to subtract two floating point values\n", onFail = true, onTimeout = true)})
    public void subDoubleOperands(){
        Assert.assertEquals(
                            "It is possible to subtract two floating point values",
                            "-1.0", i.interpret("2.0 3 sub pstack"));
    }
    @Ignore
    @Test
    public void subSymbols(){
        Assert.assertEquals(
                            "It is possible to subtract two symbolic values",
                            "-1.0", i.interpret("/two 2.0  def /three 3 def two three sub pstack"));
    }
    
    // MUL
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to multiply two integer values\n", onFail = true, onTimeout = true)})
    public void mulTwoOperands(){
        // 2 3 mul == 2 * 3
        Assert.assertEquals(
                            "It is possible to multiply two integer values",
                            "6", i.interpret("2 3 mul pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to multiply a negative integer value\n", onFail = true, onTimeout = true)})
    public void mulNegativeIntOperand(){
        Assert.assertEquals(
                            "It is possible to multiply a negative integer value",
                            "-6", i.interpret("-2 3 mul pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to multiply a negative float value by an int and it must return a negative float\n", onFail = true, onTimeout = true)})
    public void mulNegativeDoubleOperand(){
        Assert.assertEquals(
                            "It is possible to multiply a negative float value by an int and "
                            + "it must return a negative float",
                            "-6.0", i.interpret("-2.0 3 mul pstack"));
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Multiplication requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void mulOneOperand(){
        i.interpret("3 mul pstack");
        Assert.fail("Multiplication requires two numeric operands on the stack");
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Multiplication requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void mulEmpty(){
        i.interpret("mul pstack");
        Assert.fail("Multiplication requires two numeric operands on the stack");
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to multiply a float value by an int and it must return a float value\n", onFail = true, onTimeout = true)})
    public void mulDoubleIntOperands(){
        Assert.assertEquals(
                            "It is possible to multiply a float value by an int and it must return a float value",
                            "6.0", i.interpret("2.0 3 mul pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to multiply an int by a float value and it must return a float value\n", onFail = true, onTimeout = true)})
    public void mulIntDoubleOperands(){
        Assert.assertEquals(
                            "It is possible to multiply an int by a float value and it must return a float value",
                            "6.0", i.interpret("2 3.0 mul pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Operators precedence is set by the stack (1 3 4 mul add pstack) must yield 13\n", onFail = true, onTimeout = true)})
    public void operatorsPrecedenceSetByTheStack1(){
        Assert.assertEquals(
                            "Operators precedence is set by the stack (1 3 4 mul add pstack) must yield 13",
                            "13", i.interpret("1 3 4 mul add pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Operators precedence is set by the stack (1 3 mul 4 add) must yield (1*3 + 4)\n", onFail = true, onTimeout = true)})
    public void operatorsPrecedenceSetByTheStack2(){
        // 1*3 + 4
        Assert.assertEquals(
                            "Operators precedence is set by the stack (1 3 mul 4 add) must yield (1*3 + 4)",
                            "7", i.interpret("1 3 mul 4 add pstack"));
    }
    
    
    // DIV
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Div ALWAYS returns a float value\n", onFail = true, onTimeout = true)})
    public void divTwoIntOperands(){
        // Because div always returns float
        Assert.assertEquals(
                            "Div ALWAYS returns a float value",
                            ""+(2/3.0), i.interpret("2 3 div pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to divide a negative integer\n", onFail = true, onTimeout = true)})
    public void divNegativeIntOperand(){
        // Because div always returns float
        Assert.assertEquals(
                            "It is possible to divide a negative integer",
                            ""+(-2/3.0), i.interpret("-2 3 div pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to divide a float by an int and it must return a float value\n", onFail = true, onTimeout = true)})
    public void divDoubleIntOperands(){
        // 2 3 mul == 2 / 3
        Assert.assertEquals(
                            "It is possible to divide a float by an int and it must return a float value",
                            ""+(2/3.0), i.interpret("2.0 3 div pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to divide an int by a float and it must return a float value\n", onFail = true, onTimeout = true)})
    public void divIntDoubleOperands(){
        // 2 3 mul == 2 / 3
        Assert.assertEquals(
                            "It is possible to divide an int by a float and it must return a float value",
                            ""+(2/3.0), i.interpret("2 3.0 div pstack"));
    }
    
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division accepts negative float operands\n", onFail = true, onTimeout = true)})
    public void divNegativeDoubleOperand(){
        Assert.assertEquals(
                            "Division accepts negative float operands",
                            ""+(-2/3.0), i.interpret("-2.0 3 div pstack"));
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void divOneOperand(){
        i.interpret("3 div pstack");
        Assert.fail("Division requires two numeric operands on the stack");
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void divEmpty(){
        i.interpret("div pstack");
        Assert.fail("Division requires two numeric operands on the stack");
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division allows to mix floating point and integer operands (it must return a float value)\n", onFail = true, onTimeout = true)})
    public void divDoubleOperands(){
        Assert.assertEquals(
                            "Division allows to mix floating point and integer operands (it must return a float value)",
                            ""+(2/3.0), i.interpret("2.0 3 div pstack"));
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a double by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void divPositiveDoubleByZero(){
        // Infinity if it were java semantics
        i.interpret("2.0 0 div pstack");
        Assert.fail("Dividing a double by zero must throw an ArithmeticException "
                    + "(this is different from the java semantics which returns Infinity)");
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a negative double by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void divNegativeDoubleByZero(){
        // -Infinity if it were java semantics
        i.interpret("-2.0 0 div pstack");
        Assert.fail("Dividing a negative double by zero must throw an ArithmeticException "
                    + "(this is different from the java semantics which returns -Infinity)");
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a 0.0 by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void divDoubleZeroByZero(){
        // NaN if it were java semantics
        i.interpret("0.0 0.0 div pstack");
        Assert.fail("Dividing a 0.0 by zero must throw an ArithmeticException "
                    + "(this is different from the java semantics which returns NaN)");
    }
    
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a int by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void divPositiveIntegerDivByZeroThrowsException(){
        i.interpret("1 0 div pstack");
        Assert.fail("Dividing a int by zero must throw an ArithmeticException");
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a negative int by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void divNegativeIntegerDivByZeroThrowsException(){
        i.interpret("-1 0 div pstack");
        Assert.fail("Dividing a negative int by zero must throw an ArithmeticException");
    }

    // IDIV
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "iDiv ALWAYS returns an int value\n", onFail = true, onTimeout = true)})
    public void idivTwoIntOperands(){
        // Because idiv always returns int
        Assert.assertEquals(
                "iDiv ALWAYS returns an int value",
                ""+(2/3), i.interpret("2 3 idiv pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "It is possible to divide a negative integer\n", onFail = true, onTimeout = true)})
    public void idivNegativeIntOperand(){
        // Because idiv always returns int
        Assert.assertEquals(
                "It is possible to divide a negative integer",
                ""+(-2/3), i.interpret("-2 3 idiv pstack"));
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Idiv with Double and Int\n", onFail = true, onTimeout = true)})
    public void idivDoubleIntOperands(){
        // 2 3 mul == 2 / 3
        i.interpret("2.5 3 idiv pstack");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Idiv with Int and Double operands\n", onFail = true, onTimeout = true)})
    public void idivIntDoubleOperands(){
        i.interpret("2 3.0 idiv pstack");
    }

    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "IDiv negative double operand\n", onFail = true, onTimeout = true)})
    public void idivNegativeDoubleOperand(){
        i.interpret("-2.0 3 idiv pstack");
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void idivOneOperand(){
        i.interpret("3 idiv pstack");
        Assert.fail("Division requires two numeric operands on the stack");
    }
    @Test(expected=EmptyStackException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Division requires two numeric operands on the stack\n", onFail = true, onTimeout = true)})
    public void idivEmpty(){
        i.interpret("idiv pstack");
        Assert.fail("Division requires two numeric operands on the stack");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Idiv two double operands\n", onFail = true, onTimeout = true)})
    public void idivTwoDoubleOperands(){
        i.interpret("2.0 3.0 idiv pstack");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Idiv Positive Double by Zero\n", onFail = true, onTimeout = true)})
    public void idivPositiveDoubleByZero(){
        // Infinity if it were java semantics
        i.interpret("2.0 0 idiv pstack");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Idiv Negative Double by Zero\n", onFail = true, onTimeout = true)})
    public void idivNegativeDoubleByZero(){
        // -Infinity if it were java semantics
        i.interpret("-2.0 0 idiv pstack");
    }
    @Test(expected=Exception.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "IDiv Double zero by zero\n", onFail = true, onTimeout = true)})
    public void idivDoubleZeroByZero(){
        // NaN if it were java semantics
        i.interpret("0.0 0.0 idiv pstack");
    }

    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a int by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void idivPositiveIntegerDivByZeroThrowsException(){
        i.interpret("1 0 idiv pstack");
        Assert.fail("Dividing a int by zero must throw an ArithmeticException");
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a negative int by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void idivNegativeIntegerDivByZeroThrowsException(){
        i.interpret("-1 0 idiv pstack");
        Assert.fail("Dividing a negative int by zero must throw an ArithmeticException");
    }
    @Test(expected=ArithmeticException.class)
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Dividing a 0 by zero must throw an ArithmeticException\n", onFail = true, onTimeout = true)})
    public void idivZeroIntegerDivByZeroThrowsException(){
        i.interpret("0 0 idiv pstack");
        Assert.fail("Dividing a 0 by zero must throw an ArithmeticException");
    }
    
    // LOGIC operators
    @Ignore
    @Test
    public void trueEqTrue(){
        Assert.assertEquals(
                            "true must equal true",
                            "true", i.interpret("true true eq pstack"));
    }
    @Ignore
    @Test
    public void falseEqFalse(){
        Assert.assertEquals(
                            "false must equal false",
                            "true", i.interpret("false false eq pstack"));
    }
    @Ignore
    @Test
    public void trueNotEqFalse(){
        Assert.assertEquals(
                            "'true false eq pstack' must yield false",
                            "false", i.interpret("true false eq pstack"));
    }
    @Ignore
    @Test
    public void falseNeqTrue(){
        Assert.assertEquals(
                            "'false true ne pstack' must yield true",
                            "true", i.interpret("false true ne pstack"));
    }
    @Ignore
    @Test
    public void falseDoesEqFalse(){
        Assert.assertEquals(
                            "'false false ne pstack' must yield false",
                            "false", i.interpret("false false ne pstack"));
    }
    @Ignore
    @Test
    public void trueNEqFalse(){
        Assert.assertEquals(
                            "'true false ne pstack' must yield true",
                            "true", i.interpret("true false ne pstack"));
    }
    @Ignore
    @Test
    public void nestedLogicExpr(){
        Assert.assertEquals(
                            "Nested logic operations must be supported",
                            "true", i.interpret("1.0 1.0 eq true eq pstack"));
    }
    @Ignore
    @Test
    public void equalityOnSameSymbolShouldYieldTrue(){
        Assert.assertEquals(
                            "Testing equality on one same symbol should yield true",
                            "true", i.interpret("/one 1.0 def one one eq pstack"));
    }
    @Ignore
    @Test
    public void equalityOnDifferentSymbolShouldYieldTrueWhenBothHaveTheSameValue(){
        Assert.assertEquals(
                            "Testing equality on different symbols that have the same value should yield true",
                            "true", i.interpret("/a 1 def /b 1 def a b eq pstack"));
    }
    @Ignore
    @Test
    public void equalityOnDifferentSymbolShouldYieldFalseWhenTheyHaveDifferentValues(){
        Assert.assertEquals(
                            "Testing equality on different symbols that have different values should yield false",
                            "false", i.interpret("/a 1.0 def /b 2 def a b eq pstack"));
    }
    
    // PSTACK
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Wrong string concatenation\n", onFail = true, onTimeout = true)})
    public void multiplePstacks(){
        Assert.assertEquals(
                            "Multiple calls to to pstack must provoke the resulting string "
                            + "to contain the concatenation (separated with a blank) of the"
                            + "states of the stack at the moments pstack is called",
                            "4 4 2 6", i.interpret("4 pstack 2 pstack add pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "Calling pstack after popping the last result should yield an empty string (stack is empty)\n", onFail = true, onTimeout = true)})
    public void pstackAfterPoppingLastResultReturnsEmptyString(){
        Assert.assertEquals(
                            "Calling pstack after popping the last result should yield an "
                            + "empty string (stack is empty)",
                            "", i.interpret("1 1 add pop pstack"));
    }
    @Ignore
    @Test
    public void partialDefinitionCanBePrinted(){
        Assert.assertEquals(
                            "It must be possible to print the content of the stack during a "
                            + "partial definition of a symbol",
                            "/one", i.interpret("/one pstack"));
    }
    @Test
    @Grade
    @GradeFeedbacks({@GradeFeedback(message = "", onSuccess = true),
    @GradeFeedback(message = "The string produced by pstack must read bottom-up from left to right. (1 2 3 4 pstack) must yield (1 2 3 4)\n", onFail = true, onTimeout = true)})
    public void pstackMustPrintElementsBottomUp(){
        Assert.assertEquals(
                            "The string produced by pstack must read bottom-up from left to"
                            + "right. (1 2 3 4 pstack) must yield (1 2 3 4)",
                            "1 2 3 4", i.interpret("1 2 3 4 pstack"));
    }

}