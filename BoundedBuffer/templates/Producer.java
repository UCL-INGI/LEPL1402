package templates;

import java.util.List;
import java.util.function.Supplier;

public class Producer implements Runnable {

    private BoundedBuffer buf;

    private int testNbr;
    private List<Boolean> results;

    private Supplier<Integer> rng = () -> (int) ((Math.random()*1000) + 1);

    public Producer(BoundedBuffer buffer, int testNbr, List<Boolean> list){
        this.buf = buffer;
        this.testNbr = testNbr;
        this.results = list;
    }

    @Override
    public void run() {
        switch(testNbr){
            case 1:
                try{
                    this.buf.put(20);
                } catch(InterruptedException e){

                }
                break;
            case 2:
                for(int i = 0; i<3; i++) {
                    try {
                        this.buf.put(rng.get());
                    } catch (InterruptedException e) {

                    }
                }
                break;
            case 3:
                for(int i = 0; i<2; i++) {
                    try {
                        this.buf.put(i);
                    } catch (InterruptedException e) {

                    }
                }
                break;
            case 4:
                for(int i = 0; i<3; i++) {
                    try {
                        this.buf.put2(i);
                    } catch (InterruptedException e) {

                    }
                }
                break;
            case 5:
                results.add(this.buf.offer(42, 50));
                break;
        }
    }
}
