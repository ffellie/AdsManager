package app.routes.ads;

import app.components.ads.AdsPageBinder;
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
    private Group selectedGroup;
    private final GroupService service;
    public void view (AdsView view){
        this.view = view;
        view.getAdsListView().getPresenter().setParentPresenter(this);
        configureSaveChangesButton();
    }

    public void onAdChangedOrCreated (){
        view.getAdsListView().getPresenter().refreshGrid();
    }

    public void onAdClicked (long groupID){
        view.getAdEditView().getPresenter().getAd(groupID);
    }

    public void onGroupSelected (long groupID){
        selectedGroup = service.getGroupById(groupID);
        view.getAdsListView().getPresenter().getView().getAdGrid().asMultiSelect().setEnabled(true);
        view.getAdsListView().getPresenter().selectGroups(selectedGroup.getAdIDs());
    }

    public void onAddGroup (){
        view.getAdsListView().getPresenter().getView().getAdGrid().asMultiSelect().setEnabled(false);
    }

    public void onAdsSelected (Collection<Ad> ads){
        selectedGroup.getAdIDs().clear();
        selectedGroup.getAdIDs().addAll(getIDs(ads));
    }

    private void configureSaveChangesButton (){
        view.getSaveChangesButton().addClickListener(click ->{
            service.saveGroup(selectedGroup);
        });
    }

    private Set<Long> getIDs (Collection<Ad> ads){
        Set<Long> adIDs = new HashSet<>();
        ads.forEach(entry ->{
            adIDs.add(entry.getId());
        });
        return adIDs;
    }
}
