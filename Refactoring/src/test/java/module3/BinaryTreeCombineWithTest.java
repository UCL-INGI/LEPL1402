package module3;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.stream.IntStream;

// TODO Finish tests
public class BinaryTreeCombineWithTest {

    // for testing
    private static int MINIMAL_AMOUNT_OF_ELEMENTS_IN_TREE = 1;
    private static int MAXIMAL_AMOUNT_OF_ELEMENTS_IN_TREE = 17;
    private static int MINIMAL_VALUE = -15;
    private static int MAXIMAL_VALUE = 15;

    // to generate a random tree of n element(s)
    private int[] generateIntArray() {
        int size = new Random()
                .ints(1,
                        BinaryTreeCombineWithTest.MINIMAL_AMOUNT_OF_ELEMENTS_IN_TREE,
                        BinaryTreeCombineWithTest.MAXIMAL_AMOUNT_OF_ELEMENTS_IN_TREE
                )
                .sum();
        return new Random().ints(size, BinaryTreeCombineWithTest.MINIMAL_VALUE, BinaryTreeCombineWithTest.MAXIMAL_VALUE).toArray();
    }

    // to generate a tree given a int array
    private Tree1 geneateRandomTree(int[] array) {
        Tree1.Node root = null;
        root = insertLevelOrder(array, root, 0);
        return new Tree1(root);
    }

    // inspired by https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/
    private Tree1.Node insertLevelOrder(int[] arr, Tree1.Node root, int i) {
        // Base case for recursion
        if (i < arr.length) {
            root = new Tree1.Node(arr[i]);

            // insert left child
            root.left = insertLevelOrder(arr, root.left,
                    2 * i + 1);

            // insert right child
            root.right = insertLevelOrder(arr, root.right,
                    2 * i + 2);
        }
        return root;
    }

    // Credits to https://www.quora.com/How-do-I-add-two-arrays-in-Java-and-initialize-the-third-array-with-the-sum-of-the-two-corresponding-elements-from-the-two-arrays/answer/Mar-Souf
    private int[] sum(int[] a, int[] b) {
        return IntStream
                .range(0, Math.max(a.length, b.length))
                .map(index -> (index < a.length ? a[index] : 0) + (index < b.length ? b[index] : 0))
                .toArray();
    }

    @Test
    public void test1() {
        Tree1 t1 = new Tree1(
                new Tree1.Node(
                        9,
                        new Tree1.Node(6,
                                new Tree1.Node(9),
                                new Tree1.Node(2, new Tree1.Node(4), null)
                        ),
                        new Tree1.Node(14,
                                null,
                                new Tree1.Node(11)
                        )
                )
        );

        Tree1 t2 = new Tree1(
                new Tree1.Node(
                        0,
                        new Tree1.Node(-3, new Tree1.Node(8), null),
                        new Tree1.Node(8, new Tree1.Node(5, null, new Tree1.Node(1)), new Tree1.Node(6))
                )
        );

        Tree1 expected = new Tree1(
                new Tree1.Node(
                        9,
                        new Tree1.Node(3,
                                new Tree1.Node(17),
                                new Tree1.Node(2, new Tree1.Node(4), null)
                        ),
                        new Tree1.Node(22,
                                new Tree1.Node(5, null, new Tree1.Node(1)),
                                new Tree1.Node(17)
                        )
                )
        );
        assertEquals(expected.root, t1.combineWith(t2).root);
    }

    @Test
    public void test2() {
        for (int i = 0; i < 100; i++) {
            int[] data1 = generateIntArray();
            int[] data2 = generateIntArray();
            int[] result = sum(data1, data2);
            Tree1 t1 = geneateRandomTree(data1);
            Tree1 t2 = geneateRandomTree(data2);
            Tree1 expected = geneateRandomTree(result);
            assertEquals(expected.root, t1.combineWith(t2).root);
        }
    }

    @Test
    public void test_null_tree(){
        Tree1 t2 = null;
        Tree1 t3 = new Tree1(null);
        for (int i = 0; i < 100; i++) {
            int[] data1 = generateIntArray();
            Tree1 t1 = geneateRandomTree(data1);
            assertEquals(t1.root, t1.combineWith(t2).root); // passed tree is null
            assertEquals(t1.root, t3.combineWith(t1).root); // root of actual tree is null
            assertEquals(t1.root, t1.combineWith(t3).root); // root of passed tree is null
        }


    }

}
