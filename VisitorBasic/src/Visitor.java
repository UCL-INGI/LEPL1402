package src;

import java.util.List;

abstract class Visitor {

    protected List<? extends Object> filtered;

    /*
     * returns "filtered" list. Should only be called
     * after the visitor traversed the whole list.
     */
    abstract List<? extends Object> getFiltered();

    /*
     * get the Visitable elements and filter out any element
     * that is NOT an Integer
     */
    abstract void visit(Visitable visitable);
}
