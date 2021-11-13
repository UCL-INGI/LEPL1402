package module3;

import java.util.List;

/**
 * In this task, we ask you to implement two different version of an "in-order" tree traversal :
 * a recursive version and an iterative version.
 * Both function will take a Node representing the root of the tree itself and a List<Integer>
 * that you have to fill with the tree nodes' val respecting the "in-order" traversal.
 *
 * Example: the following tree, when explored in an in-order fashion, will give A,B,C,D,E,F,G,H,I.
 * This type of tree traversal is thus very useful to retrieve all nodes from a tree as an ordered list,
 * provided that the tree itself is "sorted", i.e, for each node, its left subtree only contain "smaller" nodes and its right
 * subtree only contain "greater" nodes (which is the case in the example, considering the alphabetical order).
 *
 *     .. image:: /course/LEPL1402/TreeInorder/inorder.png
 *        :scale: 100%
 *        :align: center
 *        :height: 218px
 *        :width: 240 px
 */
public class BinaryTreeInOrderTraversal {
    static class Tree {
        //BEGIN STRIP
        public static int nCalls = 0;
        //END STRIP

        public static void recursiveInorder(Node root, List<Integer> res) {
            //BEGIN STRIP
            nCalls++;
            //END STRIP
            // YOUR CODE HERE
        }


        public static void iterativeInorder(Node root, List<Integer> res) {
            //BEGIN STRIP
            nCalls++;
            //END STRIP
            // YOUR CODE HERE
        }

        static class Node {

            public int val;

            public Node left;
            public Node right;

            public Node(int val){
                this.val = val;
            }

            public boolean isLeaf(){
                return this.left == null && this.right == null;
            }

        }
    }
}
