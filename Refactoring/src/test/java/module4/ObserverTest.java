package module4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ObserverTest {

    @Test
    public void testSubscriptions(){

        WeatherStation station = new WeatherStation();

        Observer[] clients = {new Client(1), new Client(2), new Client(2), new Client(3)};

        for(Observer client : clients){
            station.addObserver(client);
        }

        assertEquals(station.getSubscribers().length, 4);

        station.removeObserver(clients[0]);

        assertEquals(station.getSubscribers().length, 3);

    }

    @Test
    public void testAlerts(){

        WeatherStation station = new WeatherStation();

        Observer[] clients = {new Client(1), new Client(2), new Client(2)};

        station.addObserver(clients[0]);
        station.addObserver(clients[1]);

        station.setAlert("rain",1);
        station.setAlert("snow", 2);

        assertTrue(clients[0].getNews().equals("rain"));
        assertTrue(clients[1].getNews().equals("snow"));
        assertTrue(clients[2].getNews().equals(""));

        station.addObserver(clients[2]);

        assertTrue(clients[2].getNews().equals("")); // Added after alert broadcast, does not receive the alert.

    }

    @Test
    public void testUpdate(){

        WeatherStation station = new WeatherStation();

        Observer[] clients = {new Client(1), new Client(2), new Client(2)};

        for(Observer client : clients){
            station.addObserver(client);
        }

        station.setAlert("rain", 1);
        assertTrue(clients[0].getNews().equals("rain"));
        assertTrue(clients[1].getNews().equals(""));

        station.setAlert("snow", 2);

        assertTrue(clients[0].getNews().equals("rain"));
        assertTrue(clients[1].getNews().equals(clients[2].getNews()));
        assertTrue(clients[1].getNews().equals("snow"));

        station.setAlert("winds", 2);

        assertTrue(clients[1].getNews().equals(clients[2].getNews()));
        assertTrue(clients[1].getNews().equals("winds"));

    }

    @Test
    public void testDuplicate(){

        Observable station = new WeatherStation();
        Observer client = new Client(0);

        station.addObserver(client);
        station.addObserver(client);

        station.removeObserver(client);

        assertTrue(station.getSubscribers().length == 0);

    }

    @Test
    public void testConstructor(){

        Observer client = new Client(3);

        assertNotNull(client.getNews());
        assertEquals(3, client.getZone());

    }
}
