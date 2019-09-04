
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class Tests {

    @Test
    @Grade
    @GradeFeedback(message = "Adding and/or removing subscribers to a station does not work\n", onFail = true, onTimeout = true)
    public void testSubscriptions(){

        MeteoStation station = new MeteoStation();

        Observer[] clients = {new Client(1), new Client(2), new Client(2), new Client(3)};

        for(Observer client : clients){
            station.addObserver(client);
        }

        assertEquals(station.getSubscribers().length, 4);

        station.removeObserver(clients[0]);

        assertEquals(station.getSubscribers().length, 3);

    }

    @Test
    @Grade
    @GradeFeedback(message = "Your code has problems to broadcast alerts to subscribers\n", onFail = true, onTimeout = true)
    public void testAlerts(){

        MeteoStation station = new MeteoStation();

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
    @Grade
    @GradeFeedback(message = "Your code has problems to update alert messages\n", onFail = true, onTimeout = true)
    public void testUpdate(){

        MeteoStation station = new MeteoStation();

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
    @Grade
    @GradeFeedback(message = "A client cannot be present 2 times in the same meteostation\n", onFail = true, onTimeout = true)
    public void testDuplicate(){
        Observable station = new MeteoStation();
        Observer client = new Client(0);

        station.addObserver(client);
        station.addObserver(client);

        station.removeObserver(client);

        assertTrue(station.getSubscribers().length == 0);
    }
}
