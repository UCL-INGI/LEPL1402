package module3;

import static org.junit.Assert.*;

import org.junit.*;

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
    private BinaryTreeCombineWith.Tree geneateRandomTree(int[] array) {
        BinaryTreeCombineWith.Tree.Node root = null;
        root = insertLevelOrder(array, root, 0);
        return new BinaryTreeCombineWith.Tree(root);
    }

    // inspired by https://www.geeksforgeeks.org/construct-complete-binary-tree-given-array/
    private BinaryTreeCombineWith.Tree.Node insertLevelOrder(int[] arr, BinaryTreeCombineWith.Tree.Node root, int i) {
        // Base case for recursion
        if (i < arr.length) {
            root = new BinaryTreeCombineWith.Tree.Node(arr[i]);

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
        BinaryTreeCombineWith.Tree t1 = new BinaryTreeCombineWith.Tree(
                new BinaryTreeCombineWith.Tree.Node(
                        9,
                        new BinaryTreeCombineWith.Tree.Node(6,
                                new BinaryTreeCombineWith.Tree.Node(9),
                                new BinaryTreeCombineWith.Tree.Node(2, new BinaryTreeCombineWith.Tree.Node(4), null)
                        ),
                        new BinaryTreeCombineWith.Tree.Node(14,
                                null,
                                new BinaryTreeCombineWith.Tree.Node(11)
                        )
                )
        );

        BinaryTreeCombineWith.Tree t2 = new BinaryTreeCombineWith.Tree(
                new BinaryTreeCombineWith.Tree.Node(
                        0,
                        new BinaryTreeCombineWith.Tree.Node(-3, new BinaryTreeCombineWith.Tree.Node(8), null),
                        new BinaryTreeCombineWith.Tree.Node(8, new BinaryTreeCombineWith.Tree.Node(5, null, new BinaryTreeCombineWith.Tree.Node(1)), new BinaryTreeCombineWith.Tree.Node(6))
                )
        );

        BinaryTreeCombineWith.Tree expected = new BinaryTreeCombineWith.Tree(
                new BinaryTreeCombineWith.Tree.Node(
                        9,
                        new BinaryTreeCombineWith.Tree.Node(3,
                                new BinaryTreeCombineWith.Tree.Node(17),
                                new BinaryTreeCombineWith.Tree.Node(2, new BinaryTreeCombineWith.Tree.Node(4), null)
                        ),
                        new BinaryTreeCombineWith.Tree.Node(22,
                                new BinaryTreeCombineWith.Tree.Node(5, null, new BinaryTreeCombineWith.Tree.Node(1)),
                                new BinaryTreeCombineWith.Tree.Node(17)
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
            BinaryTreeCombineWith.Tree t1 = geneateRandomTree(data1);
            BinaryTreeCombineWith.Tree t2 = geneateRandomTree(data2);
            BinaryTreeCombineWith.Tree expected = geneateRandomTree(result);
            assertEquals(expected.root, t1.combineWith(t2).root);
        }
    }

    @Test
    public void test_null_tree(){
        BinaryTreeCombineWith.Tree t2 = null;
        BinaryTreeCombineWith.Tree t3 = new BinaryTreeCombineWith.Tree(null);
        for (int i = 0; i < 100; i++) {
            int[] data1 = generateIntArray();
            BinaryTreeCombineWith.Tree t1 = geneateRandomTree(data1);
            assertEquals(t1.root, t1.combineWith(t2).root); // passed tree is null
            assertEquals(t1.root, t3.combineWith(t1).root); // root of actual tree is null
            assertEquals(t1.root, t1.combineWith(t3).root); // root of passed tree is null
        }


    }

}
