package app.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.server.StreamResource;

@Tag("video")
public class EmbeddedVideo extends Component implements HasSize, HasComponents {

    public EmbeddedVideo(StreamResource resource) {
        this();
        add(new EmbeddedSource(resource));
    }

    public EmbeddedVideo(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    protected EmbeddedVideo() {
        getElement().setAttribute("type", "video/mp4");
        setSizeFull();
    }
}

@Tag("source")
 class EmbeddedSource extends Component implements HasSize {

    public EmbeddedSource(StreamResource resource) {
        this();
        getElement().setAttribute("src", resource);
    }

    public EmbeddedSource(String url) {
        this();
        getElement().setAttribute("data", url);
    }

    protected EmbeddedSource() {
        getElement().setAttribute("type", "video/mp4");
        setSizeFull();
    }
}