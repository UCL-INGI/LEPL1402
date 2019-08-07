package src;

import java.util.ArrayList;
import java.util.List;

public class VisitorList2 extends Visitor {

    public VisitorList2(Class cls){
        super(cls);
    }

    @Override
    List<Object> getFiltered() {
        return filtered;
    }

    @Override
    void visit(Visitable visitable) {
        visitable.accept(this);
    }

    @Override
    void visit(Object o) {
        if(o.getClass().equals(toFilter)){
            filtered.add(o);
        }
    }
}
