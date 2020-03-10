package app.routes.groupedit;

import app.data.Strings;
import app.data.ad.Ad;
import app.data.ad.AdService;
import app.data.ad.MediaType;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.group.Promotion;
import app.data.group.PromotionRepository;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@UIScope
@RequiredArgsConstructor
public class GroupEditPresenter {
    private Group group;
    private GroupEditView view;
    private final PromotionRepository promotionRepository;
    private final AdService adService;
    private final GroupService groupService;
    public  void view (GroupEditView view){
        this.view = view;
    }

    private void addGridColumns(){
        view.getPromotionGrid().addColumn(promotion -> {return adService.getAdById(promotion.getAdID());}).setHeader(Strings.NAME).setKey("name");
        view.getPromotionGrid().addComponentColumn()
    };

    private HorizontalLayout createGridButtons (Promotion promotion){
        Ad ad = adService.getAdById(promotion.getAdID());
        Button viewButton = new Button(Strings.VIEW);
        viewButton.addClickListener(event ->{
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
        Button removeButton = new Button();
        removeButton.addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            Text text = new Text(Strings.ARE_U_SURE_PROMOTION);
            Button cancelButton = new Button(Strings.CANCEL), yesButton = new Button(Strings.YES);
            cancelButton.addClickListener(buttonClickEvent1 -> dialog.close());
            yesButton.addClickListener(buttonClickEvent1 -> {
                        view.setEnabled(false);
                        group.getPromotions().remove(promotion);
                        groupService.saveGroup(group);
                        view.getPromotionGrid().getDataProvider().refreshAll();
                        view.setEnabled(true);
                        dialog.close();
                    }
            );
            dialog.add(new VerticalLayout(text,new HorizontalLayout(cancelButton,yesButton)));
            dialog.setWidth("400px");
            dialog.setHeight("100px");
            dialog.open();
        });
        Button editPromotion = new Button(Strings.PLAYBACK_OPTIONS);
        editPromotion.addClickListener(buttonClickEvent -> {
            Dialog dialog= new Dialog()
        })
    }
}
