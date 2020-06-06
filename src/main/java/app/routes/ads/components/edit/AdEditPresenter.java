package app.routes.ads.components.edit;

import app.components.media.ImageUpload;
import app.constants.RouteURLs;
import app.constants.Strings;
import app.data.ad.Ad;
import app.data.ad.AdServiceImpl;
import app.data.ad.MediaType;
import app.data.user.User;
import app.data.user.UserService;
import app.routes.ads.AdsPageBinder;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import elemental.json.Json;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
@UIScope
@RequiredArgsConstructor
//@Validated
public class AdEditPresenter {
    private final AdServiceImpl service;
    private final UserService userService;
    private final AdsPageBinder parentPresenter;
    private MemoryBuffer receiver= new MemoryBuffer();
    private AdEditView view;
    private Ad ad;
    private Binder<Ad> binder= new Binder<>(Ad.class);
    private byte[] fileBytes;
    private boolean isFileUploaded;


    public void view (AdEditView view){
        this.view = view;
        configureImageUpload();
        bindFields();
        refresh();
    }

    private void bindFields (){
        binder.forField(view.getNameField())
                .bind(Ad::getName,Ad::setName);
        binder.forField(view.getDescriptionField())
                .bind(Ad::getDescription,Ad::setDescription);

    }

    public void refresh (){
        ad = new Ad();
        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByName(secUser.getName());
        view.getImageUpload().getElement().setPropertyJson("files", Json.createArray());
        if (user!=null)
            ad.setUser(user);
        else
            UI.getCurrent().navigate("login");
        binder.setBean(ad);
        view.getNameField().setInvalid(false);
        view.getDescriptionField().setInvalid(false);
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
                isFileUploaded = true;
            }
            catch (IOException e){
                imageUpload.getElement().setPropertyJson("files", Json.createArray());
            }
        });
        view.add(imageUpload);
    }
    public boolean save (){
        if (validateInput()) {
            try {
                File file = new File( RouteURLs.FILES_DIR + ad.getFilename());
                OutputStream outputStream = new FileOutputStream(file);
                outputStream.write(fileBytes);
//                if (ad.getMediaType()==MediaType.Video){
//                    IContainer container = IContainer.make();
//                    int result = container.open((new ByteArrayInputStream(fileBytes)), IContainerFormat.make());
//
//                }
                service.saveAd(ad);
                parentPresenter.onAdChangedOrCreated();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private boolean validateInput (){
        boolean isValid = true;
        if (ad.getName().trim().isEmpty()){
            isValid=false;
            view.getNameField().setErrorMessage(Strings.FIELD_CANNOT_BE_EMPTY);
            view.getNameField().setInvalid(true);
        }
        if (ad.getFilename()==null)
            isValid = false;
        if (!isFileUploaded){
            return false;
        }
        return isValid;
    }
}
