package src;

public class Level {

    private LevelComponent[] [] components;
    private int size;

    public Level(String components){

        ElementFactory instancier = ElementFactory.getInstance();

        int count = 0;
        for(int i = 0; i < components.length(); i++){
            if(components.charAt(i) == '\n'){
                break;
            }
            count++;
        }

        components = components.replaceAll("\n","");

        LevelComponent[] [] componentsObjects = new LevelComponent[count] [count];

        int idx=0;
        for(int i = 0; i<count; i++){
            for(int j = 0; j<count; j++){
                componentsObjects[i][j] = instancier.getElement(String.valueOf(components.charAt(idx++)));
            }
        }

        this.components = componentsObjects;
        this.size = count;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                sb.append(this.components[i][j].toString());
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public LevelComponent[][] getComponents(){
        return this.components;
    }

    public int getSize(){
        return this.size;
    }

}
