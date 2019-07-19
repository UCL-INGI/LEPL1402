package src;

public interface InterpreterInterface {

    /*
     * @pre: 'instructions' is a valid chain of PostScript instructions
     * @post: returns a String representing the state of the stack when a 'pstack' instruction is encountered.
     *   If several elements are still on the stack, separate them with whitespaces.
     *   If there is no element on the stack or no 'pstack' instruction, return the empty string ("").
     */
    public String interpret(String instructions);

}