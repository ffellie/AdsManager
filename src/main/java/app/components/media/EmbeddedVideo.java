package app.components.media;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;

@Tag("video")
public class EmbeddedVideo extends Component implements HasSize, HasComponents {
    private static final String FILE_DIR = "files/";
    private String resourceName;
    private String mimeType;
    private EmbeddedSource source;

    public EmbeddedVideo(String url) {
        this();
        source = new EmbeddedSource(url);
        resourceName = url;
        try {
            mimeType = "video/" + resourceName.split(".")[1];
        }
        catch (Exception e){}

        add(source);
    }

    protected EmbeddedVideo() {
        setSizeFull();
        getElement().setAttribute("controls", "");
    }

    public void setResourceName (String resourceName) {
        this.resourceName = resourceName;
        source.getElement().setAttribute("src", FILE_DIR + resourceName);
        try {
            mimeType = "video/" + resourceName.split(".")[1];
            source.getElement().setAttribute("type", mimeType);
        }
        catch (Exception e){}

    }
}

@Tag("source")
 class EmbeddedSource extends Component implements HasSize {

//    public EmbeddedSource(StreamResource resource) {
//        this();
//        getElement().setAttribute("src","files/" + resource);
//    }

    public EmbeddedSource(String url) {
        this();
        getElement().setAttribute("src","files/" + url);
    }

    protected EmbeddedSource() {
        getElement().setAttribute("type", "video/mp4");
        setSizeFull();
    }
}