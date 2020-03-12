package app.routes.groupedit.components;

import app.constants.Strings;
import app.data.group.Promotion;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class PromotionEditDialog extends Dialog {
    private PromotionPlaybackView promotionPlaybackView;
    private long promotionID;
    private Button cancelButton, saveButton;
    private Text caption;
    public PromotionEditDialog (PromotionPlaybackView promotionPlaybackView){
        this.promotionPlaybackView = promotionPlaybackView;
        cancelButton = new Button(Strings.CANCEL);
        saveButton = new Button(Strings.SAVE);
        caption = new Text(Strings.PLAYBACK_OPTIONS);
        configureButtonCLickListeners();
        VerticalLayout container = new VerticalLayout(caption,promotionPlaybackView,new HorizontalLayout(cancelButton,saveButton));
    }

    private void configureButtonCLickListeners (){
        cancelButton.addClickListener(buttonClickEvent -> {close();});
        saveButton.addClickListener(buttonClickEvent -> {
            if (promotionPlaybackView.getPresenter().save()){
                close();
                Notification.show(Strings.PROMOTION_SAVED);
            }
        });
    }

    public void open(Promotion promotion){
        open();
        promotionPlaybackView.getPresenter().setPromotionAndRefresh(promotion);
    }
}
