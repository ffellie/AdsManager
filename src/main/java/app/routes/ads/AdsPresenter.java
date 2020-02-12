package app.routes.ads;

import app.components.ads.AdsPageBinder;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class AdsPresenter implements AdsPageBinder {
    private AdsView view;
    public void view (AdsView view){
        this.view = view;
        view.getAdsListView().getPresenter().setParentPresenter(this);
        view.getAdEditView().getPresenter().setParentPresenter(this);
    }

    public void onAdChangedOrCreated (){
        view.getAdsListView().getPresenter().refreshGrid();
    }

    public void onAdSelected (long adID){
        view.getAdEditView().getPresenter().getAd(adID);
    }
}
