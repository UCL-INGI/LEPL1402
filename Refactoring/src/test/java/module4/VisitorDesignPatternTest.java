package module4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class VisitorDesignPatternTest {

    private Class [] cls = {Integer.class, Double.class, String.class, Long.class};

    private Supplier [] suppliers = new Supplier[]{
            () ->  (int) (Math.random()*100) +1, // int
            () -> "test", // String
            () -> (long) (Math.random()*100) + 1, // long
            () -> (Math.random()*100) + 1 // double
    };

    private Supplier<Integer> rng = () -> (int) (Math.random()*suppliers.length);


    @Test
    public void RandomTest(){

        for(Class c : cls) {

            for (int n = 0; n < 10; n++) { // repeat 10 times the test, in case of luck.


                Object[] random = new Object[30];

                for (int i = 0; i < random.length; i++) {
                    random[i] = suppliers[rng.get()].get();
                }

                VisitorDesignPattern.Visitable list = new VisitorDesignPattern.VisitableList(random);
                VisitorDesignPattern.Visitor visitor = new VisitorDesignPattern.VisitorList(c);

                visitor.visit(list);

                List<Object> result = visitor.getFiltered();

                for (int i = 0; i < random.length; i++) {

                    if (random[i].getClass().equals(c)) {
                        assertTrue(result.contains(random[i]));
                    }
                }

                for (int i = 0; i < result.size(); i++) {
                    assertEquals(c, result.get(i).getClass());
                }

            }
        }
    }

    //BEGIN STRIP
    class VisitableList2 extends VisitorDesignPattern.Visitable {

        public VisitableList2(Object[] elems){
            this.elements = elems;
        }

        @Override
        void accept(VisitorDesignPattern.Visitor visitor) {
            for(Object o : this.elements){
                visitor.visit(o);
            }
        }
    }

    class VisitorList2 extends VisitorDesignPattern.Visitor {

        public VisitorList2(Class cls){
            super(cls);
        }

        @Override
        List<Object> getFiltered() {
            return filtered;
        }

        @Override
        void visit(VisitorDesignPattern.Visitable visitable) {
            visitable.accept(this);
        }

        @Override
        void visit(Object o) {
            if(o.getClass().equals(toFilter)){
                filtered.add(o);
            }
        }
    }

    @Test
    public void VisitorTest(){
        for(Class c : cls) {

            for (int n = 0; n < 10; n++) { // repeat 10 times the test, in case of luck.


                Object[] random = new Object[30];

                for (int i = 0; i < random.length; i++) {
                    random[i] = suppliers[rng.get()].get();
                }

                VisitorDesignPattern.Visitable list = new VisitableList2(random);
                VisitorDesignPattern.Visitor visitor = new VisitorDesignPattern.VisitorList(c);

                visitor.visit(list);

                List<Object> result = visitor.getFiltered();

                for (int i = 0; i < random.length; i++) {

                    if (random[i].getClass().equals(c)) {
                        assertTrue(result.contains(random[i]));
                    }
                }

                for (int i = 0; i < result.size(); i++) {
                    assertEquals(c, result.get(i).getClass());
                }

            }
        }
    }

    @Test
    public void VisitableTest(){
        for(Class c : cls) {

            for (int n = 0; n < 10; n++) { // repeat 10 times the test, in case of luck.


                Object[] random = new Object[30];

                for (int i = 0; i < random.length; i++) {
                    random[i] = suppliers[rng.get()].get();
                }

                VisitorDesignPattern.Visitable list = new VisitorDesignPattern.VisitableList(random);
                VisitorDesignPattern.Visitor visitor = new VisitorList2(c);

                visitor.visit(list);

                List<Object> result = visitor.getFiltered();

                for (int i = 0; i < random.length; i++) {

                    if (random[i].getClass().equals(c)) {
                        assertTrue(result.contains(random[i]));
                    }
                }

                for (int i = 0; i < result.size(); i++) {
                    assertEquals(c, result.get(i).getClass());
                }

            }
        }
    }
    //END STRIP
}
