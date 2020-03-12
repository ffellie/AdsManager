package app.routes.ads.components.list;

import app.data.ad.AdService;
import app.data.ad.MediaType;
import app.routes.ads.AdsPageBinder;
import app.constants.Strings;
import app.data.ad.Ad;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@UIScope
@Getter
@Setter
@RequiredArgsConstructor
public class AdsListPresenter {
    private final AdService service;
    private AdsPageBinder parentPresenter;
    private DataProvider<Ad,Void> dataProvider;
    private AdsListView view;
    private boolean lock;
    public void view (AdsListView view){
        this.view=view;
        configureGridData();
        addActionButtons();
    }


    private void addActionButtons (){
        view.getAdGrid().addComponentColumn(this::createActionButtons);
    }

    private HorizontalLayout createActionButtons (Ad ad){
        Button selectButton = new Button(Strings.VIEW);
        selectButton.addClickListener(event ->{
            if (ad.getMediaType()== MediaType.Picture) {
                view.getMediaPreviewDialog().updatePoster(ad.getFilename());
                view.getMediaPreviewDialog().toggleImage();
            }
            else {
                view.getMediaPreviewDialog().updateVideo(ad.getFilename());
                view.getMediaPreviewDialog().toggleVideo();
            }
            view.getMediaPreviewDialog().open();
        });
        Button editButton = new Button(Strings.VIEW_EDIT_FILE);
        editButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            dialog.setHeight("300px");
            dialog.setWidth("500px");
            TextField name = new TextField(Strings.NAME);
            TextArea description = new TextArea(Strings.DESCRIPTION);
            name.setValue(ad.getName());
            if (ad.getDescription()!=null)
                description.setValue(ad.getDescription());
            name.setErrorMessage("Поле не должно быть пустым");
            Button cancelButton = new Button(Strings.CANCEL), saveButton = new Button(Strings.SAVE);
            cancelButton.addClickListener(buttonClickEvent -> dialog.close());
            saveButton.addClickListener(buttonClickEvent -> {
                if (name.getValue()!=null && !name.getValue().trim().isEmpty()){
                    ad.setDescription(description.getValue());
                    ad.setName(name.getValue());
                    view.setEnabled(false);
                    service.saveAd(ad);
                    view.setEnabled(true);
                    dialog.close();
                }
                else {
                    name.setInvalid(true);
                }
            });
            dialog.add(name,description,new HorizontalLayout(cancelButton,saveButton) );
            dialog.open();
        });
        return new HorizontalLayout(selectButton,editButton);
    }


    public void refreshGrid (){
        view.getAdGrid().getDataProvider().refreshAll();
    }

    private void configureGridData (){
        view.getAdGrid().setPageSize(50);
        dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));

                    List<Ad> persons = service
                            .findAll(offset, limit, sortOrder);

                    return persons.stream();
                },
                query -> service.count());
        dataProvider.refreshAll();
        view.getAdGrid().setDataProvider(dataProvider);
    }
}
