package src;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Observable {

    // An Observable object is the object that will broadcast informations/states/messages to
    // other specifics objects : its observers

    // The key (Integer) is the zone where the Observable is.
    // The value (String) is the meteo alert related to the zone.
    protected HashMap<Integer, String> zones = new HashMap<>();

    protected List<Observer> subscribers = new ArrayList<>();

    /*
     * Return an array containing all the observers of the station
     */
    abstract public Observer[] getSubscribers();

    /*
     * Send a message to APPROPRIATE subscribers.
     */
    abstract public void broadcast(int zone, String alert);

    /*
     * Add an observer to the subscribers of the station
     */
    abstract public void addObserver(Observer sub);

    /*
     * Remove an observer from the subscribers of the station
     */
    abstract public boolean removeObserver(Observer sub);

    /*
     * Set the current alert for a given zone. Note that
     * there must be AT MOST one alert for each zone.
     */
    abstract public void setAlert(String alert, int zone);


}
