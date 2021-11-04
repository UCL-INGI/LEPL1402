package module3;

import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Stack;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class BinaryTreeEqualsTest {

    Supplier<Integer> rnd = () -> (int) (Math.random() * 100);

    @Test
    public void randomTreeTest(){

        for(int i = 0; i < 10; i++) {

            Integer[] seeds = Stream.generate(rnd).limit(15).toArray(Integer[]::new);
            Tree2.Node root = new Tree2.Node(0);
            Tree2.Node root2 = new Tree2.Node(0);
            root = buildTree(root, seeds, 0);
            root2 = buildTree(root2, seeds, 0);
            Tree2 tree = new Tree2(root);
            Tree2 tree2 = new Tree2(root2);

            assertEquals(tree, tree2); // root.equals(root2) == true
        }
    }


    @Test
    public void simpleTreeTest(){

        Integer [] seeds = Stream.generate(rnd).limit(7).toArray(Integer[]::new);

        Tree2.Node root = new Tree2.Node(seeds[0]);
        root.left = new Tree2.Node(seeds[1]);
        root.right = new Tree2.Node(seeds[2]);
        root.left.left = new Tree2.Node(seeds[3]);
        root.left.right = new Tree2.Node(seeds[4]);
        root.right.left = new Tree2.Node(seeds[5]);
        root.right.right = new Tree2.Node(seeds[6]);

        Tree2 tree = new Tree2(root);

        Tree2.Node root2 = new Tree2.Node(seeds[0]);
        root2.left = new Tree2.Node(seeds[1]);
        root2.right = new Tree2.Node(seeds[2]);
        root2.left.left = new Tree2.Node(seeds[3]);
        root2.left.right = new Tree2.Node(seeds[4]);
        root2.right.left = new Tree2.Node(seeds[5]);
        root2.right.right = new Tree2.Node(seeds[5]);

        Tree2 tree2 = new Tree2(root2);

        assertNotEquals(tree, tree2);

        root2.right.right = new Tree2.Node(seeds[6]);

        tree2 = new Tree2(root2);

        assertEquals(tree, tree2);
    }


    @Test
    public void cornerCasesTest(){

        Tree2 tree = new Tree2(null);
        Tree2 tree2 = new Tree2(null);

        assertEquals(tree, tree2);

        tree2 = new Tree2(new Tree2.Node(1));

        assertNotEquals(tree, tree2);
        assertNotEquals(tree, null);
        assertNotEquals(tree, new Stack<>());

    }




    private Tree2.Node buildTree(Tree2.Node root, Integer [] seeds, int idx){

        if(idx < seeds.length){

            root = new Tree2.Node(seeds[idx]);

            root.left = buildTree(root.left, seeds, 2*idx+1);
            root.right = buildTree(root.right, seeds, 2*idx+2);
        }

        return root;
    }
}