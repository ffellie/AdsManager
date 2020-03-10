package app.routes.ads.components.list;

import app.components.media.MediaPreviewDialog;
import app.components.search.Search;
import app.data.ad.Ad;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
public class AdsListView extends VerticalLayout {
    private AdsListPresenter presenter;
    private Grid<Ad> adGrid;
    private Search search;
    private Text header;
    private MediaPreviewDialog mediaPreviewDialog;
    public AdsListView(AdsListPresenter presenter, AdSearchService adSearchService, MediaPreviewDialog mediaPreviewDialog) {
        this.presenter = presenter;
        this.mediaPreviewDialog = mediaPreviewDialog;
        adGrid = new Grid<>(Ad.class);
        search = new Search(adGrid, adSearchService);
        header = new Text("Список файлов");
        Label searchLabel = new Label("Поиск по файлам");
        adGrid.removeAllColumns();
        adGrid.addColumn("name");
        adGrid.addColumn("mediaType");
        add(searchLabel,search,adGrid);
        search.setFilterBoxEnabled(false);
        presenter.view(this);
    }
}
