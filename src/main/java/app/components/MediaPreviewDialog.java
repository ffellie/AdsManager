package app.components;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@UIScope
@Setter
@Getter
public class MediaPreviewDialog extends Dialog {
    private final static String FILES_DIRECTORY = "/Users/dias/projects/ad-content/";

    private String filename="";
    private Image poster;

    EmbeddedVideo video;
    public MediaPreviewDialog (){
        super();
        poster = new Image();
        setWidth("640px");
        setHeight("480px");
//        add(poster);
        poster.setSizeFull();
    }
    public void updatePoster(String filename){
        this.filename = filename;
        File file = new File(FILES_DIRECTORY + filename);
        try {
            FileInputStream fileInputStream = new FileInputStream((file));
            StreamResource resource = new StreamResource("", () -> fileInputStream);
            poster.setSrc(resource);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void updateVideo(String filename){
        this.filename = filename;
        File file = new File(FILES_DIRECTORY + filename);
        try {
            FileInputStream fileInputStream = new FileInputStream((file));
            StreamResource resource = new StreamResource("", () -> fileInputStream);
            video = new EmbeddedVideo(resource);
            add(video);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

}
