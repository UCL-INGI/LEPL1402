package module4;

import java.util.ArrayList;
import java.util.List;

/**
 * In this task, we will ask you to implement a list filtering using the visitor design pattern.
 * More precisely, your visitor will need to traverse a list full of objects of different kinds.
 * At the end of its traversal, your visitor should have computed a filtered list containing only Integer elements from the original list.
 * To succeed at this task, you'll have to give us the implementation of these two classes:
 *
 * These two classes must extend Visitable and Visitor.
 * These are abstract classes, so be sure to implement all abstract methods these classes contain.
 *
 * Here is an example of how your code is supposed to run:
 *
 *         Visitor visitor = new VisitorList(Integer.class);
 *         Visitable visitable = new VisitableList(new Object[]{1, 2, 3, 3.1, 4, "HELLO"});
 *
 *         visitor.visit(visitable);
 *
 *         List<Object> lst = visitor.getFiltered(); // [1, 2, 3, 4]
 */
class VisitorList extends Visitor {
    //BEGIN STRIP
    public VisitorList(Class cls) {
        super(cls);
    }

    @Override
    List<Object> getFiltered() {
        return null;
    }

    @Override
    void visit(Visitable visitable) {

    }

    @Override
    void visit(Object o) {

    }
    //END STRIP
    // YOUR CODE HERE
}

class VisitableList extends Visitable {

    //BEGIN STRIP
    public VisitableList(Object[] o) {
        super();
    }

    @Override
    void accept(Visitor visitor) {

    }
    //END STRIP
    // YOUR CODE HERE
}

abstract class Visitor {
    protected Class toFilter;
    protected List<Object> filtered;

    public Visitor(Class cls){
        this.filtered = new ArrayList<>();
        this.toFilter = cls;
    }

    /*
     * returns the "filtered" list. Should only be called
     * after the visitor has traversed the whole list.
     */
    abstract List<Object> getFiltered();

    abstract void visit(Visitable visitable);

    /*
     * If o is an instance of 'toFilter', adds it to filtered.
     */
    abstract void visit(Object o);
}

abstract class Visitable {
    protected Object [] elements;

    abstract void accept(Visitor visitor);
}
