package module5;


import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * In this task, you have to implement this well-known full adder logic circuit:
 *
 *     .. figure:: /course/LEPL1402/Generics3/full-adder-circuit.png
 *        :scale: 100 %
 *        :alt: alternate text
 *        :align: center
 *        :figclass: align-center
 *
 * Instead of computing the result directly by using a chain of logical operators (which can lead to error prone code),
 * you're asked to implement the various logic gates the circuit requires first.
 * The way you'll implement these is by defining, for each logic gate, a method that returns a BiFunction or Function that computes the logical operation.
 *      - Function: https://docs.oracle.com/javase/8/docs/api/java/util/function/Function.html
 *      - BiFunction: https://docs.oracle.com/javase/8/docs/api/java/util/function/BiFunction.html
 *
 * Once you've managed to define these logic gates, you're asked to implement the whole circuit within the
 * evaluate_circuit(Boolean a, Boolean b, Boolean carry_in) method.
 *
 * You are not allowed to use any of the built-in logic operators within this method (i.e., you cannot use &, |, ! or ^ within this method).
 * Do note this method returns a Map<String, Boolean>, which stores the two outputs of the computation under keys SUM and carry_out.
 *
 * Your task is thus to implement the following class: Evaluator.
 */
class Evaluator {

    public BiFunction<Boolean, Boolean, Boolean> xor_gate() {
        // TODO BY STUDENT
        return null;
    }

    public BiFunction<Boolean, Boolean, Boolean> or_gate() {
        // TODO BY STUDENT
        return null;
    }

    public BiFunction<Boolean, Boolean, Boolean> and_gate() {
        // TODO BY STUDENT
        return null;
    }

    public Function<Boolean, Boolean> not_gate() {
        // TODO BY STUDENT
        return null;
    }

    // Should return a map containing the computation's results (use HashMap)
    // You're asked to store the results under the following keys: "SUM" & "carry_out"
    // TODO WARNING : ONLY USE the previously defined methods to compute the result
    // (INGInious will prevent you from cheating by directly invoking logical operators)
    public Map<String, Boolean> evaluate_circuit(Boolean a, Boolean b, Boolean carry_in) {
        // TODO BY STUDENT
        return null;
    }

}
