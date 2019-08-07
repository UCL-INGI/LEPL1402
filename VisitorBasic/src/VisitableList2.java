package src;


public class VisitableList2 extends Visitable {

    public VisitableList2(Object[] elems){
        this.elements = elems;
    }

    @Override
    void accept(Visitor visitor) {
        for(Object o : this.elements){
            visitor.visit(o);
        }
    }
}
