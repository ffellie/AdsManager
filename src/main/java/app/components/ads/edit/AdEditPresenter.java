package app.components.ads.edit;

import app.components.ads.AdService;
import app.components.ads.AdServiceImpl;
import app.components.ads.AdsPageBinder;
import app.components.media.ImageUpload;
import app.data.ad.Ad;
import app.data.ad.MediaType;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.Json;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
@UIScope
@RequiredArgsConstructor
public class AdEditPresenter {
    private final AdService service;
    private final static String FILES_DIRECTORY = "/Users/dias/projects/ad-content/";
    private MemoryBuffer receiver= new MemoryBuffer();
    private AdEditView view;
    private Ad ad;
    private Binder<Ad> binder= new Binder<>(Ad.class);
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
            configureDialog();
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
                configureDialog();
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
    private void configureDialog (){
        if (ad.getMediaType()==MediaType.Picture) {
            view.getDialog().updatePoster(ad.getFilename());
            view.getDialog().toggleImage();
        }
        else {
            view.getDialog().updateVideo(ad.getFilename());
            view.getDialog().toggleVideo();
        }
        view.getViewMediaButton().setEnabled(true);
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
