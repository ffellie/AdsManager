package app.components.groups;

import app.data.group.Group;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class GroupsView extends VerticalLayout {
    private final GroupsPresenter presenter;
    private Grid<Group> groupsGrid;
    public GroupsView (GroupsPresenter presenter){
        super();
        this.presenter = presenter;
        groupsGrid = new Grid<>(Group.class);
        presenter.view(this);
    }
}
