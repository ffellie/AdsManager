package app.components.adedit;

import app.components.ImageUpload;
import app.data.Ad;
import app.data.MediaType;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.Json;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
@UIScope
public class AdEditPresenter {
    public AdEditPresenter(){
        receiver = new MemoryBuffer();
        binder= new Binder<>(Ad.class);
    };
    private final static String FILES_DIRECTORY = "/Users/dias/projects/ad-content/";
    @Autowired
    private AdService service;
    private MemoryBuffer receiver;
    private AdEditView view;
    private Ad ad;
    private Binder<Ad> binder;
    private boolean isFileUploaded;
    private static final int DEFAULT_DURATION = 30;
    private byte[] fileBytes;

    @Getter
    @Setter
    private AdsPageBinder parentPresenter;

    public void view (AdEditView view){
        this.view = view;
        configureImageUpload();
        configureSaveButton();
        configureViewButton();
        bindFields();

    }
    public void configureViewButton (){
        view.getViewMediaButton().addClickListener(buttonClickEvent -> {
            view.getDialog().open();
        });

    }

    private void bindFields (){
        binder.forField(view.getNameField())
                .bind(Ad::getName,Ad::setName);
        binder.forField(view.getDescriptionField())
                .bind(Ad::getDescription,Ad::setDescription);
        getAd(0);
    }

    public void getAd (long adID){
        if (adID==0)
            ad = new Ad();
        else
            ad = service.getAdById(adID);
        binder.setBean(ad);
        if (ad.getFilename()!=null) {
            view.getDialog().updatePoster(ad.getFilename());
            view.getDialog().updateVideo(ad.getFilename());

            view.getViewMediaButton().setEnabled(true);
        }
        else {
            view.getViewMediaButton().setEnabled(false);
        }
        isFileUploaded = false;
    }

    private void configureImageUpload (){
        ImageUpload imageUpload = new ImageUpload(receiver);
        view.setImageUpload(imageUpload);
        imageUpload.addSucceededListener(event -> {
            try {
                fileBytes=IOUtils.toByteArray(receiver.getInputStream());
                String mime = event.getMIMEType();
                String extension = mime.split("/")[1];
                String type = mime.split("/")[0];
                String filename = UUID.randomUUID().toString() + "." + extension;
                if (type.equals("video"))
                    ad.setMediaType(MediaType.Video);
                else ad.setMediaType(MediaType.Picture);
                ad.setFilename(filename);
                isFileUploaded=true;
                view.getDialog().updatePoster(filename);
                view.getDialog().updateVideo(filename);

                view.getViewMediaButton().setEnabled(true);
                imageUpload.getElement().setPropertyJson("files", Json.createArray());
            }
            catch (IOException e){
                e.printStackTrace();
            }
        });
        view.add(imageUpload);
    }
    private void configureSaveButton (){
        view.getSaveButton().addClickListener(buttonClickEvent -> {
            if (validateInput()) {
                if (isFileUploaded) {
                    try {
                        File file = new File( FILES_DIRECTORY + ad.getFilename());
                        OutputStream outputStream = new FileOutputStream(file);
                        outputStream.write(fileBytes);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                service.saveAd(ad);
                ad = null;
                parentPresenter.onAdChangedOrCreated();
            }
        });

    }

    private boolean validateInput (){
        boolean isValid = true;
        if (ad.getName().trim().isEmpty()){
            isValid=false;
            view.getNameField().setInvalid(true);
            view.getNameField().setErrorMessage("");
        }
        if (ad.getFilename()==null)
            isValid = false;
        if (view.getDurationField().getValue()==null || view.getDurationField().getValue()<0){
            ad.setDuration(DEFAULT_DURATION);
        }
        else {
            ad.setDuration(view.getDurationField().getValue().intValue());
        }

        return isValid;
    }


}
