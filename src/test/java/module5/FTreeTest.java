package module5;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class FTreeTest {

    private static Random rng = new Random();

    public static int randomInt(){
        return rng.nextInt(100);
    }

    public static int fTreeSum(FTree<Integer> ft){
        if(ft instanceof FTree.Node){
            return ft.value() + fTreeSum(ft.left()) + fTreeSum(ft.right());
        }
        return ft.value();
    }

    @Test()
    public void testSimpleMap() {
        //BEGIN STRIP
        FTree.resetCounter();
        //END STRIP

        FTree<Integer> root;
        FTree[] firstStage = new FTree[2];
        FTree[] secondStage = new FTree[4];
        FTree[] thirdStage = new FTree[8];
        FTree[] fleafs = new FTree[16];

        for(int i = 0 ; i < 16 ; i++){
            fleafs[i] = new FTree.Leaf(1+(2*i));
        }

        for(int i = 0; i<8 ; i++){
            thirdStage[i] = new FTree.Node<Integer>(2 + i*4, fleafs[i*2], fleafs[i*2+1]);
        }

        for(int i = 0 ; i <4 ; i++){
            secondStage[i] = new FTree.Node<Integer>(((i*8)+4), thirdStage[i*2], thirdStage[i*2+1]);
        }

        firstStage[0] = new FTree.Node<Integer>(8, secondStage[0], secondStage[1]);
        firstStage[1] = new FTree.Node<Integer>(24, secondStage[2], secondStage[3]);

        root = new FTree.Node<Integer>(16, firstStage[0], firstStage[1]);

        FTree<Integer> result = root.map(i -> i*10);

        assertEquals(4960, fTreeSum(result) );

        //BEGIN STRIP
        if(FTree.counter < 2){
            fail("Your map method must be recursive");
        }
        //BEGIN STRIP
    }

    @Test()
    public void testRandomMap() {
        //BEGIN STRIP
        FTree.resetCounter();
        //BEGIN STRIP
        int elemSum = 0 ;
        int r;

        FTree<Integer> root;
        FTree[] firstStage = new FTree[2];
        FTree[] secondStage = new FTree[4];
        FTree[] thirdStage = new FTree[8];
        FTree[] fleafs = new FTree[16];
        for(int i = 0 ; i < 16 ; i++){
            r = randomInt();
            elemSum += r;
            fleafs[i] = new FTree.Leaf(r);
        }

        for(int i = 0; i<8 ; i++){
            r = randomInt();
            elemSum += r;
            thirdStage[i] = new FTree.Node<Integer>(r, fleafs[i*2], fleafs[i*2+1]);
        }

        for(int i = 0 ; i <4 ; i++){
            r = randomInt();
            elemSum += r;
            secondStage[i] = new FTree.Node<Integer>(r, thirdStage[i*2], thirdStage[i*2+1]);
        }

        r = randomInt();
        elemSum += r;
        firstStage[0] = new FTree.Node<Integer>(r, secondStage[0], secondStage[1]);

        r = randomInt();
        elemSum += r;
        firstStage[1] = new FTree.Node<Integer>(r, secondStage[2], secondStage[3]);

        r = randomInt();
        elemSum += r;
        root = new FTree.Node<Integer>(r, firstStage[0], firstStage[1]);

        FTree<Integer> result = root.map(i -> i*10);

        assertEquals(elemSum*10, fTreeSum(result) );

        //BEGIN STRIP
        if(FTree.counter < 2){
            fail("Your map method must be recursive");
        }
        //BEGIN STRIP
    }

    @Test()
    public void testDepth() {
        //BEGIN STRIP
        FTree.resetCounter();
        //END STRIP

        FTree<Integer> root;
        FTree[] firstStage = new FTree[2];
        FTree[] secondStage = new FTree[4];
        FTree[] thirdStage = new FTree[8];
        FTree[] fleafs = new FTree[16];
        for(int i = 0 ; i < 16 ; i++){
            fleafs[i] = new FTree.Leaf(1);
        }

        for(int i = 0; i<8 ; i++){
            thirdStage[i] = new FTree.Node<Integer>(1, fleafs[i*2], fleafs[i*2+1]);
        }

        for(int i = 0 ; i <4 ; i++){
            secondStage[i] = new FTree.Node<Integer>(1, thirdStage[i*2], thirdStage[i*2+1]);
        }

        firstStage[0] = new FTree.Node<Integer>(1, secondStage[0], secondStage[1]);
        firstStage[1] = new FTree.Node<Integer>(1, secondStage[2], secondStage[3]);

        root = new FTree.Node<Integer>(1, firstStage[0], firstStage[1]);

        assertEquals(4, root.depth());

        //BEGIN STRIP
        if(FTree.counter < 2){
            fail("Your map method must be recursive");
        }
        //END STRIP
    }

    @Test()
    public void testLeaf() {

        FTree<Integer> l = new FTree.Leaf(1);

        if(null!=l.left()) fail("left() on a leaf should return null");

        if(null!=l.right()) fail("right() on a leaf should return null");

        if(1!=l.value()) fail("The value returned by value() is not the one given to the constructor");

        assertEquals(null, l.left());
        assertEquals(null, l.right());


    }

    @Test()
    public void testNode() {

        FTree<Integer> l = new FTree.Leaf(1);
        FTree<Integer> r = new FTree.Leaf(3);
        FTree<Integer> n = new FTree.Node(2, l, r);

        if(l!=n.left()) fail("left() on a leaf should return the child");

        if(r!=n.right()) fail("right() on a leaf should return the child");

        if(2!=n.value()) fail("The value returned by value() is not the one given to the constructor");

        assertEquals(l, n.left());
        assertEquals(r, n.right());
    }

}
