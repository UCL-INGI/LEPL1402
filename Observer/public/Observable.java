import java.util.ArrayList;
import java.util.List;

abstract class Observable {

    // An Observable object is the object that will broadcast informations/states/messages to
    // other specifics objects : its observers

    // String is the meteo alert, Integer is the zone id
    protected List<Pair<String, Integer>> zones = new ArrayList<>();

    protected List<Observer> subscribers = new ArrayList<>();

    /*
     * Return an array containing all the observers of the station
     */
    abstract public Observer[] getSubscribers();

    /*
     * Send a message to APPROPRIATE subscribers.
     */
    abstract public void broadcast(Pair<String, Integer> pair);

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
     * there must be AT MOST one alert for each zone and
     * when an alert is set, subscribers of the zone must
     * receive a message
     */
    abstract public void setAlert(String alert, int zone);


}
