package src;

abstract class AbstractLevel {

    protected LevelComponent[] [] components;
    protected int size;

    @Override
    abstract public String toString();

    abstract LevelComponent[][] getComponents();

    abstract int getSize();

}
