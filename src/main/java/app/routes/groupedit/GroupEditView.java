package app.routes.groupedit;

import app.components.media.MediaPreviewDialog;
import app.constants.Strings;
import app.data.promotion.Promotion;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
@Setter
public class GroupEditView extends VerticalLayout {
    private GroupEditPresenter presenter;
    private Grid<Promotion> promotionGrid;
    private Text caption = new Text("");
    private Button addButton = new Button(Strings.ADD_PROMOTION), backButton = new Button(Strings.BACK);
    private MediaPreviewDialog mediaPreviewDialog;

    public GroupEditView(GroupEditPresenter presenter, MediaPreviewDialog mediaPreviewDialog) {
        this.presenter = presenter;
        this.mediaPreviewDialog = mediaPreviewDialog;
        promotionGrid = new Grid<>(Promotion.class);
        presenter.view(this);
//        promotionGrid.removeAllColumns();
        HorizontalLayout buttonsContainer = new HorizontalLayout(backButton, addButton);
        add(caption, promotionGrid, buttonsContainer);
    }

}
