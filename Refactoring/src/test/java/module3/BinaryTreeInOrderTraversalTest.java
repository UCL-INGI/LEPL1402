package module3;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.junit.Assume.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BinaryTreeInOrderTraversalTest {

    private Supplier<Integer> rnd = () -> (int) (Math.random() * 100);

    private static boolean recursivePass = false;
    private static boolean iterativePass = false;

    private int [] matching = new int[]{3, 1, 4, 0, 5, 2};

    
    @Test
    public void test1(){
        //BEGIN STRIP
        BinaryTreeInOrder.nCalls = 0;
        //END STRIP
        Integer [] nodes = Stream.generate(rnd).limit(6).toArray(Integer[]::new);

        Node node = new Node(nodes[0]);
        buildTree(node,nodes);

        ArrayList<Integer> res = new ArrayList<>();
        BinaryTreeInOrder.recursiveInorder(node, res);
        //BEGIN STRIP
        assertEquals(BinaryTreeInOrder.nCalls, nodes.length);
        //END STRIP

        for(int i =0; i < nodes.length; i++){
            assertEquals( res.get(i), nodes[matching[i]] );
        }

        recursivePass = true;

    }

    @Test
    public void test2(){
        //BEGIN STRIP
        BinaryTreeInOrder.nCalls = 0;
        //END STRIP

        Integer [] nodes = Stream.generate(rnd).limit(6).toArray(Integer[]::new);

        Node node = new Node(nodes[0]);
        buildTree(node,nodes);

        ArrayList<Integer> res = new ArrayList<>();
        BinaryTreeInOrder.iterativeInorder(node, res);

        //BEGIN STRIP
        assertEquals(1, BinaryTreeInOrder.nCalls);
        //END STRIP

        for(int i =0; i < nodes.length; i++){
            assertEquals( res.get(i), nodes[matching[i]] );
        }

        iterativePass = true;
    }

    @Test
    public void test3(){

        assumeTrue(iterativePass && recursivePass); // Skip this test if the two previous one didn't pass.

        Integer [] nodes = Stream.generate(rnd).limit(6).toArray(Integer[]::new);

        Node nodeIt = new Node(nodes[0]);
        Node nodeRec = new Node(nodes[0]);
        buildTree(nodeIt, nodes);
        buildTree(nodeRec, nodes);

        ArrayList<Integer> it = new ArrayList<>();
        ArrayList<Integer> rec = new ArrayList<>();
        BinaryTreeInOrder.iterativeInorder(nodeIt, it);
        BinaryTreeInOrder.recursiveInorder(nodeRec, rec);

        assertEquals(it.size(), rec.size());

        for(int i=0; i < it.size(); i++){
            assertEquals(it.get(i), rec.get(i));
        }

    }

    private void buildTree(Node node, Integer[] nodes){
        node.left = new Node(nodes[1]);
        node.right = new Node(nodes[2]);
        node.left.left = new Node(nodes[3]);
        node.left.right = new Node(nodes[4]);
        node.right.left = new Node(nodes[5]);
    }

}