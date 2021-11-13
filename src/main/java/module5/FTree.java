package module5;

import java.util.function.Function;

/**
 * In this exercise you have to complete classes FTree, Node and Leaf.
 * Ftree is an abstract class that represents a binary tree. It is extended by Node and Leaf.
 * A Node is an FTree with two children (left and right) and a value.
 * A Leaf is terminal, so it has a value but no children.
 *
 * Within the FTree class, you have to recursively implement:
 *      - depth(): returns the depth of the tree (we assume that the tree is balanced)
 *      - map(): returns a new FTree obtained by applying the function received as argument on all the values contained in the tree
 */
public abstract class FTree<A> {

    //BEGIN STRIP
    public static int counter=0;

    public static void resetCounter(){
        counter=0;
    }
    //END STRIP

    public final int depth() {
        //BEGIN STRIP
        counter++;
        //END STRIP

        //TODO by student
        return 0;
    }

    public abstract A value();
    public abstract FTree<A> left();
    public abstract FTree<A> right();

    public final <B> FTree<B> map(Function<A,B> f) {
        //BEGIN STRIP
        counter++;
        //END STRIP

        //TODO by student
        return null;
    }

    /**
     * To complete the Leaf_ class, you must :
     *      - implement the constructor
     *      - extend FTree
     */
    static class Leaf<A> extends FTree<A> {

        private final A value;

        public Leaf(A a){
            //BEGIN STRIP
            value = a;
            //END STRIP
        }

        //BEGIN STRIP
        @Override
        public A value() {
            return null;
        }

        @Override
        public FTree<A> left() {
            return null;
        }

        @Override
        public FTree<A> right() {
            return null;
        }
        //END STRIP

        //TODO by student
    }

    /**
     * To complete the Node class, you must:
     *      - implement the constructor
     *      - extend FTree
     */
    static class Node<A> extends FTree<A> {

        private final A value;
        private final FTree<A> left, right;

        public Node(final A a, FTree<A> left,  FTree<A> right){
            //BEGIN STRIP
            value = a;
            this.left = left;
            this.right = right;
            //END STRIP
        }

        //BEGIN STRIP
        @Override
        public A value() {
            return null;
        }

        @Override
        public FTree<A> left() {
            return null;
        }

        @Override
        public FTree<A> right() {
            return null;
        }
        //END STRIP

        //TODO by student
    }

}
