package module3;

/**
 * In this task, we will ask you to implement the mehtod equals(Object o) for both classes Tree and Node.
 * Two instances of ``Tree`` are considered equal when they both have the exact same structure
 * (same number of nodes, each node at the same place in both trees) and when every node has the same val.
 */
class Tree2 {
    public Node root;

    public Tree2(Node root){
        this.root = root;
    }

    @Override
    public boolean equals(Object o){
        // YOUR CODE HERE
        return false;
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

        @Override
        public boolean equals(Object o){
            // YOUR CODE HERE
            return false;
        }
    }
}
