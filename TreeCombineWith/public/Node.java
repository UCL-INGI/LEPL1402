public class Node {

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