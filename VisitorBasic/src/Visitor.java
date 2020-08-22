package src;

import java.util.ArrayList;
import java.util.List;

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

