package app.components.ads.list;

import app.components.ads.AdService;
import app.components.ads.AdServiceImpl;
import app.components.ads.AdsPageBinder;
import app.data.ad.Ad;
import app.data.group.Group;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@UIScope
@Getter
@Setter
@RequiredArgsConstructor
public class AdsListPresenter {
    private final AdService service;
    private AdsPageBinder parentPresenter;
    private DataProvider<Ad,Void> dataProvider;
    private AdsListView view;
    private boolean lock;
    public void view (AdsListView view){
        this.view=view;
        configureGridData();
        addActionButtons();
        configureAdsSelectedEvent();
        configureAddButton();
    }


    private void addActionButtons (){
        view.getAdGrid().addComponentColumn(this::createSelectButton);
    }
    private void configureAddButton (){
        view.getAddButton().addClickListener(click ->{
            parentPresenter.onAdClicked(0);
        });
    }
    private Button createSelectButton (Ad ad){
        Button selectButton = new Button("Просмотр");
        selectButton.addClickListener(event ->{
            parentPresenter.onAdClicked(ad.getId());
        });
        return selectButton;
    }

    public void selectGroups (Collection<Long> adIDs){
        lock = true;
        view.getAdGrid().asMultiSelect().deselectAll();
        view.getAdGrid().asMultiSelect().select(service.getAllByID(adIDs));
        lock = false;
    }

    public void configureAdsSelectedEvent (){
        view.getAdGrid().addSelectionListener(event -> {
            if (!lock)
                parentPresenter.onAdsSelected(event.getAllSelectedItems());
        });
    }

    public void refreshGrid (){
        view.getAdGrid().getDataProvider().refreshAll();
    }

    private void configureGridData (){
        view.getAdGrid().setPageSize(50);
        dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));

                    List<Ad> persons = service
                            .findAll(offset, limit, sortOrder);

                    return persons.stream();
                },
                query -> service.count());
        dataProvider.refreshAll();

        view.getAdGrid().setDataProvider(dataProvider);
    }
}
