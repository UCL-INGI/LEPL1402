abstract class Observer {

    // A subscriber is an "observer" object. It will get updates from a meteo station.

     protected String news;

     protected int zone;

    /*
     * Update the variable "news"
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

