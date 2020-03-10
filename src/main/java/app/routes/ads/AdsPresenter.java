package app.routes.ads;

import app.components.media.MediaPreviewDialog;
import app.data.ad.Ad;
import app.data.group.Group;
import app.data.group.GroupService;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@UIScope
@RequiredArgsConstructor
public class AdsPresenter implements AdsPageBinder {
    private AdsView view;

    public void view (AdsView view){
        this.view = view;
        view.getAdsListView().getPresenter().setParentPresenter(this);
        view.getAddAdButton().addClickListener(buttonClickEvent -> view.getAddAdDialog().open());
    }

    public void onAdChangedOrCreated (){
        view.getAdsListView().getPresenter().refreshGrid();
    }



    public void onAddGroup (){
        view.getAdsListView().getPresenter().getView().getAdGrid().asMultiSelect().setEnabled(false);
    }

    private Set<Long> getIDs (Collection<Ad> ads){
        Set<Long> adIDs = new HashSet<>();
        ads.forEach(entry ->{
            adIDs.add(entry.getId());
        });
        return adIDs;
    }
}
