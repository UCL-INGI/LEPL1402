package module6;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class FutureEasyTest {
    //BEGIN STRIP

    private Supplier<Integer> rng = () -> (int) (Math.random()*100);

    private List<FutureEasy.URL> generateURLList(){
        int length = rng.get();
        List<FutureEasy.URL> urls = new ArrayList<FutureEasy.URL>(length);
        for(int i = 0; i<length; i++){
            urls.add(new FutureEasy.URL(new FutureEasy.Image(rng.get())));
        }
        return urls;
    }

    @Test
    public void testLoadImage(){
        List<FutureEasy.URL> urls = generateURLList();
        FutureEasy.WebPage webPage = new FutureEasy.WebPage(10, new FutureEasy.HTML(urls));
        List<Future<FutureEasy.Image>> futures = new ArrayList<Future<FutureEasy.Image>>(urls.size());
        for(FutureEasy.URL url : webPage.getHtml().getUrls()){
            futures.add(webPage.loadImage(url));
        }

        List<FutureEasy.Image> images = new ArrayList<FutureEasy.Image>(futures.size());
        try{
            for(Future<FutureEasy.Image> future : futures){
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
    public void testLoadImages(){
        List<FutureEasy.URL> urls = generateURLList();
        FutureEasy.WebPage webPage = new FutureEasy.WebPage(10, new FutureEasy.HTML(urls));
        List<Future<FutureEasy.Image>> futures = webPage.loadImages(urls);
        List<FutureEasy.Image> images = new ArrayList<FutureEasy.Image>(futures.size());
        try{
            for(Future<FutureEasy.Image> future : futures){
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
    //END STRIP
}
