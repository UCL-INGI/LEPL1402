
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;

import java.util.Random;
import java.util.stream.IntStream;

// TODO Finish tests
public class Tests {

    // for testing
    private static int MINIMAL_AMOUNT_OF_ELEMENTS_IN_TREE = 1;
    private static int MAXIMAL_AMOUNT_OF_ELEMENTS_IN_TREE = 17;
    private static int MINIMAL_VALUE = -15;
    private static int MAXIMAL_VALUE = 15;

    // to generate a random tree of n element(s)
    private int[] generateIntArray() {
        int size = new Random()
                .ints(1,
                        Tests.MINIMAL_AMOUNT_OF_ELEMENTS_IN_TREE,
                        Tests.MAXIMAL_AMOUNT_OF_ELEMENTS_IN_TREE
                )
                .sum();
        return new Random().ints(size, Tests.MINIMAL_VALUE, Tests.MAXIMAL_VALUE).toArray();
    }

    // to generate a tree given a int array
    private Tree geneateRandomTree(int[] array) {
        Node root = null;
        root = insertLevelOrder(array, root, 0);
        return new Tree(root);
    }

    // inspired by https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/
    private Node insertLevelOrder(int[] arr, Node root, int i) {
        // Base case for recursion
        if (i < arr.length) {
            root = new Node(arr[i]);

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
        Tree t1 = new Tree(
                new Node(
                        9,
                        new Node(6,
                                new Node(9),
                                new Node(2, new Node(4), null)
                        ),
                        new Node(14,
                                null,
                                new Node(11)
                        )
                )
        );

        Tree t2 = new Tree(
                new Node(
                        0,
                        new Node(-3, new Node(8), null),
                        new Node(8, new Node(5, null, new Node(1)), new Node(6))
                )
        );

        Tree expected = new Tree(
                new Node(
                        9,
                        new Node(3,
                                new Node(17),
                                new Node(2, new Node(4), null)
                        ),
                        new Node(22,
                                new Node(5, null, new Node(1)),
                                new Node(17)
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
            Tree t1 = geneateRandomTree(data1);
            Tree t2 = geneateRandomTree(data2);
            Tree expected = geneateRandomTree(result);
            assertEquals(expected.root, t1.combineWith(t2).root);
        }
    }

    @Test
    public void test_null_tree(){
        Tree t2 = null;
        Tree t3 = new Tree(null);
        for (int i = 0; i < 100; i++) {
            int[] data1 = generateIntArray();
            Tree t1 = geneateRandomTree(data1);
            assertEquals(t1.root, t1.combineWith(t2).root); // passed tree is null
            assertEquals(t1.root, t3.combineWith(t1).root); // root of actual tree is null
            assertEquals(t1.root, t1.combineWith(t3).root); // root of passed tree is null
        }


    }

}
