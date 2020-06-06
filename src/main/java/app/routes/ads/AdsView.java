package app.routes.ads;


import app.constants.Strings;
import app.routes.ads.components.AddAdDialog;
import app.routes.ads.components.edit.AdEditView;
import app.routes.ads.components.list.AdsListView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@UIScope
@Component
@Getter
@Setter
public class AdsView extends VerticalLayout {
    private AdsPresenter presenter;
    private AdEditView adEditView;
    private AdsListView adsListView;
    private AddAdDialog addAdDialog;
    private Button addAdButton = new Button(Strings.ADD_NEW_FILE);

    public AdsView(AdsPresenter presenter, AdsListView adsListView, AdEditView adEditView, AddAdDialog addAdDialog) {
        this.presenter = presenter;
        this.adEditView = adEditView;
        this.adsListView = adsListView;
        this.addAdDialog = addAdDialog;
        presenter.view(this);
        add(adsListView, addAdButton);
        configureComponentsPlacementAndSize();
    }

    private void configureComponentsPlacementAndSize() {
        setAlignItems(Alignment.CENTER);
        adsListView.setWidth("50%");
        adsListView.setMaxWidth("800px");
        adsListView.setMinWidth("400px");
    }
}
