package app.routes;


import app.components.adedit.AdEditView;
import app.components.adedit.AdsListView;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@UIScope
@Component
@Getter
@Setter
public class AdsView extends HorizontalLayout  {
    private AdsPresenter presenter;
    private AdEditView adEditView;
    private AdsListView adsListView;
    public AdsView(AdsPresenter presenter, AdsListView adsListView, AdEditView adEditView){
        this.presenter = presenter;
        this.adEditView = adEditView;
        this.adsListView = adsListView;
        presenter.view(this);
        add(adsListView);
        add(adEditView);
    }
}
