package module3;

/**
 * Write a method combineWith that could be added to the Tree class.
 *
 * The method accepts another binary tree of integers as a parameter and combines the two trees into a new third tree which is returned.
 * The new tree's structure should be a union of the structures of the two original trees.
 *
 * It should have a node in any location where there was a node in either of the original trees (or both).
 *
 * The nodes of the new tree should store an integer indicating the sum of the values at that position of the original trees if possible.
 * (else only the value of the not null node)
 *
 * For example, suppose Tree variables t1 and t2 have been initialized and store the following trees:
 *
 *     .. figure:: /course/LEPL1402/TreeCombineWith/example-part1.png
 *        :scale: 100 %
 *        :alt: alternate text
 *        :align: center
 *        :figclass: align-center
 *
 * Then the following call:
 *
 *     .. code-block:: java
 *
 *        Tree t3 = t1.combineWith(t2);
 *
 * Will return a reference to the following tree:
 *
 *     .. figure:: /course/LEPL1402/TreeCombineWith/example-part2.png
 *        :scale: 100 %
 *        :alt: alternate text
 *        :align: center
 *        :figclass: align-center
 *
 * You may define private helper methods to solve this problem but in any case, your method should not change the structure or contents of either of the two trees being compared.
 */
public class BinaryTreeCombineWith {
    static class Tree {
        public Node root;

        public Tree(Node root){
            this.root = root;
        }

        public Tree combineWith(Tree o){
            // YOUR CODE HERE
            return null;
        }

        static class Node {

            public int val;
            public Node left;
            public Node right;

            public Node(int val){
                this.val = val;
            }

            public Node(int val, Node left, Node right) {
                this.val = val;
                this.left = left;
                this.right = right;
            }

            public boolean isLeaf(){
                return this.left == null && this.right == null;
            }

            @Override
            public boolean equals(Object o) {
                if (!(o instanceof Node)) return false;

                Node other = (Node) o;

                if (this.val != other.val) return false;
                if (this.isLeaf() != other.isLeaf()) return false;
                if (this.left != null && !this.left.equals(other.left)) return false;
                if (this.right != null && !this.right.equals(other.right)) return false;

                return true;
            }
        }
    }
}
