package app.routes.groupedit.components;

import app.constants.Strings;
import app.data.promotion.Promotion;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
@UIScope
@Getter
@Setter
public class PromotionEditDialog extends Dialog {
    private PromotionPlaybackView promotionPlaybackView;

    private Long promotionID;

    private Button cancelButton, saveButton;

    private Text caption;

    private Text fileName;

    private Button chooseFileButton;

    private FilePicker filePicker;

    public PromotionEditDialog(PromotionPlaybackView promotionPlaybackView, FilePicker filePicker) {
        this.promotionPlaybackView = promotionPlaybackView;
        this.filePicker=filePicker;

        filePicker.setDialog(this);
        promotionPlaybackView.getPresenter().setDialog(this);

        cancelButton = new Button(Strings.CANCEL);
        saveButton = new Button(Strings.SAVE);

        caption = new Text(Strings.PLAYBACK_OPTIONS);

        fileName = new Text("");
//        fileName.setId("filename");
//        Label fileLabel = new Label(Strings.AD);
//
//        fileLabel.setFor(fileName);
        chooseFileButton = new Button(Strings.CHOOSE_AD);


        configureButtonCLickListeners();
        VerticalLayout container = new VerticalLayout(caption, new HorizontalLayout(fileName, chooseFileButton), promotionPlaybackView, new HorizontalLayout(cancelButton, saveButton));
        add(container);
    }

    private void configureButtonCLickListeners() {
        cancelButton.addClickListener(buttonClickEvent -> {
            close();
        });
        saveButton.addClickListener(buttonClickEvent -> {
            if (promotionPlaybackView.getPresenter().save()) {
                close();
                Notification.show(Strings.PROMOTION_SAVED);
            }
        });

        chooseFileButton.addClickListener(click ->{
           filePicker.open();
            try {
                filePicker.getAdsListView().getAdGrid().removeColumnByKey("pick");
            }
            catch (Exception e){};
            filePicker.addPickButton();
        });
    }

    public void open(Long promotion, Long group) {
        open();
        promotionPlaybackView.getPresenter().setPromotionAndRefresh(promotion, group);
    }
}
