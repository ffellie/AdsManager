package app.routes.usergroups;

import app.components.search.Search;
import app.constants.Strings;
import app.data.group.Group;
import app.data.user.User;
import app.routes.usergroups.components.AddUserToGroupDialog;
import app.routes.usergroups.services.GroupSearchService;
import app.routes.usergroups.services.UserSearchService;
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
public class UserGroupsView extends HorizontalLayout {
    private UserGroupsPresenter presenter;
    private Grid<Group> userGroupGrid;
    private Grid<User> userGrid;
    private Search groupSearch;
    private Text groupsCaption = new Text("asd"), userCaption = new Text("asdf");
    private Button addGroupButton;
    private Button addUserButton;
    private AddUserToGroupDialog addUserToGroupDialog;

    public UserGroupsView (UserGroupsPresenter presenter, GroupSearchService groupSearchService, UserSearchService userSearchService,AddUserToGroupDialog addUserToGroupDialog){
        this.presenter = presenter;
        userGroupGrid = new Grid<>(Group.class);
        userGroupGrid.removeAllColumns();
        userGroupGrid.addColumn("name");
        userGrid = new Grid<>(User.class);
        userGrid.removeAllColumns();
        userGrid.addColumn("name");
        groupSearch = new Search(userGroupGrid, groupSearchService);
        groupSearch.setFilterBoxEnabled(false);
        addGroupButton = new Button(Strings.ADD_GROUP);
        addUserButton = new Button(Strings.ADD_USER_TO_GROUP);
        this.addUserToGroupDialog = addUserToGroupDialog;
        VerticalLayout h1 = new VerticalLayout(groupsCaption, userGroupGrid, addGroupButton);
        VerticalLayout h2 = new VerticalLayout(userCaption, userGrid, addUserButton);
        h1.setWidth("50%");
        add(h1,h2);
        configurePositionsAndSize();
        presenter.view(this);
    }

    private void configurePositionsAndSize(){
        setAlignItems(Alignment.CENTER);
        userGroupGrid.setMinWidth("600px");
    }
}
