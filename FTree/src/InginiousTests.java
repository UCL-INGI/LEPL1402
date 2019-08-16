package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import templates.*;

import static org.junit.Assert.*;


@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {



    @Test()
    @Grade(value=10, cpuTimeout=100)
    @GradeFeedback(message = "You failed the example", onFail = true, onTimeout = true)
    public void testDepth(){

        FTree<Integer> root;
        
        FTree[] firstStage = new FTree[2];
        FTree[] secondStage = new FTree[4];
        FTree[] thirdStage = new FTree[8];
        FTree[] fleafs = new FTree[16];
        for(int i = 0 ; i < 16 ; i++){
            fleafs[i] = new Leaf();
        }

        for(int i = 0; i<8 ; i++){
            thirdStage[i] = new Node<Integer>(((i+1)*2)-1, fleafs[i*2], fleafs[i*2+1]);
        }

        for(int i = 0 ; i <4 ; i++){
            secondStage[i] = new Node<Integer>(((i*4)+2), thirdStage[i*2], thirdStage[i*2+1]);
        }

        firstStage[0] = new Node<Integer>(4, secondStage[0], secondStage[1]);
        firstStage[1] = new Node<Integer>(12, secondStage[2], secondStage[3]);

        root = new Node<Integer>(8, firstStage[0], firstStage[1]);

        FTree<Integer> result = root.map(i -> i*10);

        root.depth


    }


}
