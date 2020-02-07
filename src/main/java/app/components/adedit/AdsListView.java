package app.components.adedit;

import app.data.Ad;
import app.routes.AdsView;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
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

    public AdsListView(AdsListPresenter presenter) {
        this.presenter = presenter;
        adGrid = new Grid<>(Ad.class);
        adGrid.removeAllColumns();
        adGrid.addColumn("name");
        adGrid.addColumn("duration");
        add(adGrid);
        presenter.view(this);
    }
}
