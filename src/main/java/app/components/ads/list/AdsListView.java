package app.components.ads.list;

import app.components.search.Search;
import app.data.ad.Ad;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

//@Route(value = "ads", layout = AdsView.class)
@Component
@UIScope
@Getter
public class AdsListView extends VerticalLayout {
    private AdsListPresenter presenter;
    private Grid<Ad> adGrid;
    private Search search;
    private Button addButton;
    public AdsListView(AdsListPresenter presenter, AdSearchService adSearchService) {
        this.presenter = presenter;
        adGrid = new Grid<>(Ad.class);
        search = new Search(adGrid, adSearchService);
        addButton = new Button("Добавить рекламу");
        adGrid.removeAllColumns();
        adGrid.addColumn("name");
        adGrid.addColumn("duration");
        adGrid.addColumn("mediaType");
        add(search,adGrid, addButton);
        GridMultiSelectionModel<Ad> selectionModel = (GridMultiSelectionModel<Ad>) adGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        selectionModel.setSelectAllCheckboxVisibility(GridMultiSelectionModel.SelectAllCheckboxVisibility.VISIBLE);
        presenter.view(this);
    }
}
