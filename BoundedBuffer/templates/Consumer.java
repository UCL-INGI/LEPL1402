package templates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Consumer implements Runnable {

    private BoundedBuffer buf;
    private List<Integer> elems;
    private int testNbr;

    private Supplier<Integer> rng2 = () -> (int) ((Math.random()*100) + 1);

    public Consumer(BoundedBuffer buffer, int testNbr, List<Integer> elems){
        this.buf = buffer;
        this.elems = elems;
        this.testNbr = testNbr;
    }

    @Override
    public void run() {
        switch(testNbr){
            case 1:
                try{
                    this.buf.take();
                } catch (InterruptedException e){

                }
                break;
            case 2:
                for(int i = 0; i<2; i++) {
                    try {
                        elems.add(this.buf.take());
                    } catch (InterruptedException e) {

                    }
                }
                break;
            case 3:
                try{
                    elems.add(this.buf.take2());
                }catch (InterruptedException e){

                }
                break;
            case 4:
                elems.add(this.buf.poll(50));
                break;
        }


    }
}
