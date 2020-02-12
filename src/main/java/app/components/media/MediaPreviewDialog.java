package app.components.media;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Setter
@Getter
public class MediaPreviewDialog extends Dialog {
    private final static String FILES_DIRECTORY = "files/";

    private String filename="";
    private Image poster;
    public void toggleImage(){
        try {
            remove(video);
        }
        catch (Exception e){};
        add(poster);
    }
    public void toggleVideo(){

        try {
            remove(poster);
        }
        catch (Exception e){};
        add(video);
    }
    EmbeddedVideo video;
    public MediaPreviewDialog (){
        super();
        video=new EmbeddedVideo("");
        add(video);
        poster = new Image();
        setWidth("900px");
        setHeight("600px");
        poster.setHeightFull();
    }
    public void updatePoster(String filename){
        this.filename = filename;
        poster.setSrc(FILES_DIRECTORY + filename);
    }

    public void updateVideo(String filename){
        this.filename = filename;
        video.setResourceName(filename);
        add(video);
    }

}
