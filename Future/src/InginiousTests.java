package src;

import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Supplier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import java.util.*;
import templates.*;

import static org.junit.Assert.*;

@RunWith(GradingRunner.class) // classic "jail runner" from Guillaume's library
public class InginiousTests {

    private Supplier<Integer> rng = () -> (int) (Math.random()*100);

    private List<URL> generateURLList(){
      int length = rng.get();
      List<URL> urls = new ArrayList<URL>(length);
      for(int i = 0; i<length; i++){
        urls.add(new URL(new Image(rng.get())));
      }
      return urls;
    }

    @Test
    @Grade(value=1, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    @GradeFeedback(message = "Your loadImage function does not work. Is your constructor complete?", onFail = true, onTimeout = true)
    public void testLoadImage(){
      List<URL> urls = generateURLList();
        WebPage webPage = new WebPage(10, new HTML(urls));
        List<Future<Image>> futures = new ArrayList<Future<Image>>(urls.size());
        for(URL url : webPage.getHtml().getUrls()){
            futures.add(webPage.loadImage(url));
        }

        List<Image> images = new ArrayList<Image>(futures.size());
        try{
            for(Future<Image> future : futures){
                images.add(future.get());
            }
        } catch(InterruptedException e){

        } catch (ExecutionException e){

        }

        assertEquals(images.size(), urls.size());
        for(int i = 0; i<images.size(); i++){
            assertEquals(images.get(i).getNum(), urls.get(i).getImage().getNum());
        }

        webPage.getExecutor().shutdown();
    }

    @Test
    @Grade(value=1, cpuTimeout=1000, customPermissions = ThreadPermissionFactory.class)
    @GradeFeedback(message="Your loadImages function does not work", onFail=true, onTimeout=true)
    public void testLoadImages(){
      List<URL> urls = generateURLList();
        WebPage webPage = new WebPage(10, new HTML(urls));
        List<Future<Image>> futures = webPage.loadImages(urls);
        List<Image> images = new ArrayList<Image>(futures.size());
        try{
            for(Future<Image> future : futures){
                images.add(future.get());
            }
        } catch(InterruptedException e){

        } catch (ExecutionException e){

        }

        assertEquals(images.size(), urls.size());
        for(int i = 0; i<images.size(); i++){
            assertEquals(images.get(i).getNum(), urls.get(i).getImage().getNum());
        }

        webPage.getExecutor().shutdown();
    }
}
