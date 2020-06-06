package app.routes.groupedit.components;

import app.constants.Strings;
import app.data.ad.Ad;
import app.data.ad.AdService;
import app.data.promotion.Promotion;
import app.routes.ads.components.list.AdSearchService;
import app.routes.ads.components.list.AdsListView;
import app.routes.groupedit.GroupEditPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;


@Component
@Getter
@Setter
@UIScope
public class FilePicker extends Dialog {
    private AdsListView adsListView;

    private Button cancelButton;

    private Long adv;

    private PromotionEditDialog dialog;

    private GroupEditPresenter groupEditPresenter;

    public FilePicker(AdsListView adsListView) {
        super(adsListView);
        this.adsListView = adsListView;
        cancelButton = new Button(Strings.CANCEL);

        add(cancelButton);
        setMinWidth("600px");

        cancelButton.addClickListener(buttonClickEvent -> close());
    }

    public void addPickButton() {
        adsListView
                .getAdGrid()
                .addComponentColumn(ad -> {
            Button pick = new Button(Strings.CHOOSE_AD);

            pick.addClickListener(click -> {
                dialog.getPromotionPlaybackView().getPresenter().getPromotion().setAdID(adv = ad.getId());
                dialog.getFileName().setText(ad.getName());
                try {
                    adsListView.getAdGrid().removeColumnByKey("pick");
                }
                catch (IllegalArgumentException e){};
                close();
                groupEditPresenter.refreshGrid();
            });

            return pick;
        }).setHeader("pick");
    }
}
