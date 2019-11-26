package templates;

public class Cat extends Animal {

    // useful for the actForTestMethod function
    private final String forTestMethod = "Thinking";

    public Cat() {
        // TODO : How can you invoke the Animal constructor from here (with name = "Cat") ?
        @   @cat_constructor@@
    }

    public void actForTestMethod() {
        // TODO : How could you invoke the parent act method from here with action parameter : the String forTestMethod
        @   @cat_test_act@@
    }

}
