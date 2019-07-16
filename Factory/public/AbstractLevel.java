public abstract class AbstractLevel {

    protected LevelComponent[] [] components;
    protected int size;

    @Override
    abstract public String toString();

    abstract public LevelComponent[][] getComponents();

    abstract public int getSize();

}
