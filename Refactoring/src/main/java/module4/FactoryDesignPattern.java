package module4;

/**
 * In this task, we will ask you to implement a labyrinth mini-game level builder using the factory design pattern.
 * Each level consists in a set of Wall, Key, Floor, Door, all of them implementing the LevelComponent interface.
 * Your goal is thus to implement the following two classes:
 *
 * Note that these two classes extend the abstract classes Factory and AbstractLevel.
 * Do note that we add a small constraint to this exercise: your ElementFactory must be a singleton
 * (i.e, it should not be possible to instantiate your factory with Java's new keyword).
 * For your Level class, what we want you to do is, given a String, create a Level object whose components is a 2D array where each cell represent a LevelComponent and whose size is the total number of components in the level.
 * Note that all sub-arrays in components will have the same size as we will only ask you to create "square" labyrinths.
 * If a character does not correspond to one of the components, your code should throw an IllegalArgumentException.
 */
public class FactoryDesignPattern {
    static class ElementFactory extends Factory {

        // YOUR CODE HERE
        //BEGIN STRIP
        @Override
        LevelComponent getElement(char c) {
            return null;
        }
        //END STRIP

        public static ElementFactory getInstance() {
            // YOUR SINGLETON HERE
            return null;
        }
    }

    static class Level extends AbstractLevel {

        public Level(String input) {
            // YOUR CODE HERE
        }

        //BEGIN STRIP
        @Override
        public String toString() {
            return null;
        }

        @Override
        LevelComponent[][] getComponents() {
            return new LevelComponent[0][];
        }

        @Override
        int getSize() {
            return 0;
        }
        //END STRIP

        /* Example of level building with this String : String s = "#-K\n-D-\n#-K\n"
         * Gives : LevelComponent[][] componentsObjects = {
         *                                        {new Wall(), new Floor(), new Key()},
         *                                        {new Floor(), new Door(), new Floor()},
         *                                        {new Wall(), new Floor(), new Key()}}
         */


        // YOUR CODE HERE
    }

    abstract static class Factory {
        abstract LevelComponent getElement(char c);
    }

    abstract static class AbstractLevel {
        protected LevelComponent[][] components;
        protected int size;

        /*
         * Return a string representation of this level
         */
        @Override
        abstract public String toString();

        abstract LevelComponent[][] getComponents();

        abstract int getSize();
    }

    interface LevelComponent {
        public String draw();
    }

    static class Door implements LevelComponent {
        public Door(){}

        public String draw(){
            return "D";
        }
    }

    static class Floor implements LevelComponent {
        public Floor(){}

        public String draw(){
            return "-";
        }
    }

    static class Key implements LevelComponent {
        public Key(){}

        public String draw(){
            return "K";
        }
    }

    static class Wall implements LevelComponent {

        public Wall(){}

        public String draw(){
            return "#";
        }
    }
}

