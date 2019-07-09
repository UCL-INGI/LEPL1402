package src;

public class ElementFactory {

    private static ElementFactory instance;

    private ElementFactory() {}

    public LevelComponent getElement(String str){
        switch (str){
            case "#":
                return new Wall();
            case "-":
                return new Floor();
            case "K":
                return new Key();
            case "D":
                return new Door();
            default:
                return null;
        }
    }

    public static ElementFactory getInstance(){

        if(instance == null){
            instance = new ElementFactory();
        }
        return instance;

    }

}
