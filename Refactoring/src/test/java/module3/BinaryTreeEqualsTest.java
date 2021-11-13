package module3;

import org.junit.Test;

import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class BinaryTreeEqualsTest {

    Supplier<Integer> rnd = () -> (int) (Math.random() * 100);

    @Test
    public void randomTreeTest(){

        for(int i = 0; i < 10; i++) {

            Integer[] seeds = Stream.generate(rnd).limit(15).toArray(Integer[]::new);
            BinaryTreeEquals.Tree.Node root = new BinaryTreeEquals.Tree.Node(0);
            BinaryTreeEquals.Tree.Node root2 = new BinaryTreeEquals.Tree.Node(0);
            root = buildTree(root, seeds, 0);
            root2 = buildTree(root2, seeds, 0);
            BinaryTreeEquals.Tree tree = new BinaryTreeEquals.Tree(root);
            BinaryTreeEquals.Tree tree2 = new BinaryTreeEquals.Tree(root2);

            assertEquals(tree, tree2); // root.equals(root2) == true
        }
    }


    @Test
    public void simpleTreeTest(){

        Integer [] seeds = Stream.generate(rnd).limit(7).toArray(Integer[]::new);

        BinaryTreeEquals.Tree.Node root = new BinaryTreeEquals.Tree.Node(seeds[0]);
        root.left = new BinaryTreeEquals.Tree.Node(seeds[1]);
        root.right = new BinaryTreeEquals.Tree.Node(seeds[2]);
        root.left.left = new BinaryTreeEquals.Tree.Node(seeds[3]);
        root.left.right = new BinaryTreeEquals.Tree.Node(seeds[4]);
        root.right.left = new BinaryTreeEquals.Tree.Node(seeds[5]);
        root.right.right = new BinaryTreeEquals.Tree.Node(seeds[6]);

        BinaryTreeEquals.Tree tree = new BinaryTreeEquals.Tree(root);

        BinaryTreeEquals.Tree.Node root2 = new BinaryTreeEquals.Tree.Node(seeds[0]);
        root2.left = new BinaryTreeEquals.Tree.Node(seeds[1]);
        root2.right = new BinaryTreeEquals.Tree.Node(seeds[2]);
        root2.left.left = new BinaryTreeEquals.Tree.Node(seeds[3]);
        root2.left.right = new BinaryTreeEquals.Tree.Node(seeds[4]);
        root2.right.left = new BinaryTreeEquals.Tree.Node(seeds[5]);
        root2.right.right = new BinaryTreeEquals.Tree.Node(seeds[5]);

        BinaryTreeEquals.Tree tree2 = new BinaryTreeEquals.Tree(root2);

        assertNotEquals(tree, tree2);

        root2.right.right = new BinaryTreeEquals.Tree.Node(seeds[6]);

        tree2 = new BinaryTreeEquals.Tree(root2);

        assertEquals(tree, tree2);
    }


    @Test
    public void cornerCasesTest(){

        BinaryTreeEquals.Tree tree = new BinaryTreeEquals.Tree(null);
        BinaryTreeEquals.Tree tree2 = new BinaryTreeEquals.Tree(null);

        assertEquals(tree, tree2);

        tree2 = new BinaryTreeEquals.Tree(new BinaryTreeEquals.Tree.Node(1));

        assertNotEquals(tree, tree2);
        assertNotEquals(tree, null);
        assertNotEquals(tree, new Stack<>());

    }




    private BinaryTreeEquals.Tree.Node buildTree(BinaryTreeEquals.Tree.Node root, Integer [] seeds, int idx){

        if(idx < seeds.length){

            root = new BinaryTreeEquals.Tree.Node(seeds[idx]);

            root.left = buildTree(root.left, seeds, 2*idx+1);
            root.right = buildTree(root.right, seeds, 2*idx+2);
        }

        return root;
    }
}