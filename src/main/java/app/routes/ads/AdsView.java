package app.routes.ads;


import app.components.ads.edit.AdEditView;
import app.components.ads.list.AdsListView;
import app.components.groups.GroupsView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    private GroupsView groupsView;
    private Button saveChangesButton;
    public AdsView(AdsPresenter presenter, AdsListView adsListView, AdEditView adEditView, GroupsView groupsView){
        this.presenter = presenter;
        this.adEditView = adEditView;
        this.adsListView = adsListView;
        this.groupsView = groupsView;
        saveChangesButton = new Button("Сохранить изменения");
        presenter.view(this);
        add(groupsView);
        add(new VerticalLayout(saveChangesButton,adsListView));
        add(adEditView);
    }
}
