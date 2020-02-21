package app.routes.usergroups;

import app.data.group.Group;
import app.data.user.User;
import app.data.usergroup.UserGroup;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
@Setter
public class UserGroupsView extends HorizontalLayout {
    private UserGroupsPresenter presenter;
    private Grid<Group> userGroupGrid;
    private Grid<User> userGrid;
    public UserGroupsView (UserGroupsPresenter presenter){
        this.presenter = presenter;
    }
}
