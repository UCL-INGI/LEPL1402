import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Evaluator {

    public BiFunction<Boolean, Boolean, Boolean> xor_gate() {
        // TODO BY STUDENT
    }

    public BiFunction<Boolean, Boolean, Boolean> or_gate() {
        // TODO BY STUDENT
    }

    public BiFunction<Boolean, Boolean, Boolean> and_gate() {
        // TODO BY STUDENT
    }

    public Function<Boolean, Boolean> not_gate() {
        // TODO BY STUDENT
    }

    // Should return a map containing the computation's results (use HashMap)
    // You're asked to store the results under the following keys: "SUM" & "carry_out"
    // TODO WARNING : ONLY USE the previously defined methods to compute the result
    // (INGInious will prevent you from cheating by directly invoking logical operators)
    public Map<String, Boolean> evaluate_circuit(Boolean a, Boolean b, Boolean carry_in) {
        // TODO BY STUDENT
    }

}