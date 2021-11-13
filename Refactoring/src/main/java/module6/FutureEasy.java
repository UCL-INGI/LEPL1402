package module6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * For this task you will learn use Future and ExecutorService in order to load a web page with images asynchronously.
 *      - Future: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/Future.html
 *      - ExecutorService: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html
 *
 * Indeed html is lightweight compared to the images so we want the images to be downloaded asynchronously while the html is displayed without the images.
 *
 * In this task we give you the WebPage class to complete and three other class to use : URL, Image and HTML.
 */
public class FutureEasy {
    static class WebPage {

        private ExecutorService executor;
        private HTML html;

        //BEGIN STRIP
        public HTML getHtml() {
            return html;
        }
        public ExecutorService getExecutor(){
            return this.executor;
        }
        //END STRIP

        /*
         * Bound executor to a fixed thread pool size
         */
        public WebPage(int threadNumber, HTML html){
            //TODO
        }

        /*
         * submit the download of the image specified by the url
         * to be executed by thread pool
         */
        public Future<Image> loadImage(URL url){
            //TODO
            return null;
        }

        /*
         * Download the image specified by the url
         */
        public Image downloadImageFromURL(URL url){
            //HIDDEN
            return null;
        }

        /*
         * Load all images of the page
         */
        public List<Future<Image>> loadImages(List<URL> urls){
            //TODO
            return null;
        }

        /*
         * Load the page while images are being downloaded
         */
        private void displayPageWithoutImage(){
            //HIDDEN
        }

        /*
         * Display all images on the page
         */
        private void displayImages(List<Image> images){
            //HIDDEN
        }

        /*
         * load the page
         */
        public void loadPage(){
            // First the image are downloaded asynchronously
            List<Future<Image>> futures = loadImages(this.html.getUrls());
            // While the image are being downloaded, we display the page without them
            displayPageWithoutImage();
            // Then we need all images to display them
            List<Image> images = new ArrayList<Image>(futures.size());
            try{
                for(Future<Image> future : futures){
                    // the "get()" function is waiting for the result of the future task (here download the images)
                    images.add(future.get());
                }
            } catch(InterruptedException e){

            } catch (ExecutionException e){

            }
            // we can display now images on the page
            displayImages(images);
            // shut down the executor service now
            executor.shutdown();

        }
    }

    static class HTML {

        private List<URL> urls;

        public HTML(){
            //HIDDEN TO YOU
        }

        //BEGIN STRIP
        public HTML(List<URL> urls){
            this.urls = urls;
        }
        //END STRIP

        public List<URL> getUrls(){
            return this.urls;
        }
    }

    static class URL {
        //BEGIN STRIP
        private Image image;

        public URL(Image image){
            this.image = image;
        }

        public Image getImage() {
            return image;
        }
        //END STRIP
        //HIDDEN
    }

    static class Image {
        //BEGIN STRIP
        private int num;

        public Image(int num){
            this.num = num;
        }

        public int getNum() {
            return num;
        }
        //END STRIP
        //HIDDEN
    }
}
