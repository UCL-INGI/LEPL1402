package src;

import java.util.*;

public class MeteoStation extends Observable {


    public void setAlert(String alert, int zone){
        Pair<String, Integer> pair = new Pair<>(alert, zone);
        super.zones.remove(pair); //Remove the one with an old alert
        super.zones.add(pair); //Add the one with the new alert
        //Looks weird, but check the implementation of Pair::equals and ArrayList API.
        broadcast(pair);
    }

    public void addObserver(Observer sub) {
        if(!subscribers.contains(sub)) {
            this.subscribers.add(sub);
        }
    }

    public boolean removeObserver(Observer sub) {
        return this.subscribers.remove(sub);
    }

    public void broadcast(Pair<String, Integer> pair) {
        for (Observer sub : this.subscribers) {
            if(sub.getZone() == pair.getZone()) {
                sub.update(pair.getAlert());
            }
        }
    }

    public Observer[] getSubscribers(){
        return this.subscribers.toArray(new Observer[this.subscribers.size()]);
    }

}
