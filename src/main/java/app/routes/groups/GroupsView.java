package app.routes.groups;

import app.components.search.Search;
import app.data.Strings;
import app.data.group.Group;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Component
@UIScope
@Getter
@Setter
public class GroupsView extends VerticalLayout {
    private final GroupsPresenter presenter;
    private Grid<Group> groupsGrid;
    private TextField groupName;

    public GroupsView (GroupsPresenter presenter){
        super();
        this.presenter = presenter;
        groupName = new TextField();
        groupsGrid = new Grid<>(Group.class);
        add(groupsGrid);
        groupsGrid.setSizeFull();
        groupsGrid.removeAllColumns();
        presenter.view(this);
    }
}
