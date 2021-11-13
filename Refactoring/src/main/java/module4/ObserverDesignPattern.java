package module4;

import java.util.ArrayList;
import java.util.List;

/**
 *  In this task, you have to implement the Observer design pattern in the context of a weather station:
 *  Note that Observable and Observer are two abstract classes containing abstract methods.
 *  You should therefore check and read them carefully in order to implement things correctly.
 *  For your Client class, you have to extend the Observer class and implement a constructor
 *  (do not forget instance variables).
 *  For the WeatherStation class, extend the Observable abstract class.
 *  You'll have to use Pair objects for this one.
 *
 *  Note that your observers start to receive messages at the moment they subscribe but they should not see messages that were broadcast before they subscribed.
 *  They should only get sent weather alerts relative to their own zone.
 */
public class ObserverDesignPattern {
    static class WeatherStation extends Observable {
        //BEGIN STRIP
        @Override
        public Observer[] getSubscribers() {
            return new Observer[0];
        }

        @Override
        public void broadcast(Pair<String, Integer> pair) {

        }

        @Override
        public void addObserver(Observer sub) {

        }

        @Override
        public boolean removeObserver(Observer sub) {
            return false;
        }

        @Override
        public void setAlert(String alert, int zone) {

        }
        //END STRIP
    }

    static class Client extends Observer {
        //BEGIN STRIP
        public Client(int i) {

        }

        @Override
        public void update(Object o) {

        }

        @Override
        public int getZone() {
            return 0;
        }

        @Override
        public String getNews() {
            return null;
        }
        //END STRIP
    }

    static abstract class Observer {
        // A subscriber is an "observer" object. It will get updates from a weather station.

        protected String news;

        protected int zone;

        /*
         * Updates the variable "news"
         */
        abstract public void update(Object o);

        /*
         * Getter for zone
         */
        abstract public int getZone();

        /*
         * Getter for news
         */
        abstract public String getNews();
    }

    static abstract class Observable {
        // An Observable object is the object that will broadcast information/state/messages to
        // other specific objects: its observers

        // String is the weather alert, Integer is the id of the zone to which the alert is relevant
        protected List<Pair<String, Integer>> zones = new ArrayList<>();

        protected List<Observer> subscribers = new ArrayList<>();

        /*
         * Returns an array containing all the observers of the station
         */
        abstract public Observer[] getSubscribers();

        /*
         * Sends a message to APPROPRIATE subscribers.
         */
        abstract public void broadcast(Pair<String, Integer> pair);

        /*
         * Adds an observer to the subscribers of the station
         */
        abstract public void addObserver(Observer sub);

        /*
         * Removes an observer from the subscribers of the station
         */
        abstract public boolean removeObserver(Observer sub);

        /*
         * Sets the current alert for a given zone. Note that
         * there must be AT MOST one alert for each zone and
         * when an alert is set, subscribers of the zone must
         * receive a message
         */
        abstract public void setAlert(String alert, int zone);
    }

    class Pair<String, Integer> {

        private String alert;
        private Integer zone;

        public Pair(String alert, Integer zone){
            this.alert = alert;
            this.zone = zone;
        }

        public Integer getZone() {
            return zone;
        }

        public String getAlert() {
            return alert;
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof Pair){
                Pair that = (Pair) o;
                return that.getZone() == this.getZone();
            }
            return false;
        }

    }
}
