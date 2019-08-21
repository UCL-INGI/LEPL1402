package templates;

import java.util.List;

public class HTML {

    private List<URL> urls;

    public HTML(List<URL> urls){
        this.urls = urls;
    }

    public List<URL> getUrls(){
        return this.urls;
    }
}
