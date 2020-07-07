package app.routes.usergroups.components;

import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.User;
import app.data.user.UserService;
import app.routes.usergroups.UserGroupsPresenter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@UIScope
public class AddUserToGroupDialog extends Dialog {
    private UserService service;

    private Grid<User> userGrid;

    private Group group;

    private GroupService groupService;

    private UserGroupsPresenter userGroupsView;

    public AddUserToGroupDialog(UserService service, GroupService groupService, UserGroupsPresenter userGroupsView) {
        super();
        this.service = service;
        this.groupService = groupService;
        this.userGroupsView = userGroupsView;
        userGrid = new Grid<>(User.class);
        userGrid.removeAllColumns();
        userGrid.addColumns("name", "role");
        userGrid.addComponentColumn(this::createAddButton);
        add(userGrid);
        setSizeFull();
        userGrid.setWidth("700px");
        userGrid.setHeight("600px");
        configureGridDataProvider();
    }

    public void open(Group group) {
        super.open();
        this.group = group;
        configureGridDataProvider();
        userGrid.getDataProvider().refreshAll();

    }


    private Button createAddButton(User user) {
        Button button = new Button("Добавить");
        button.addClickListener(buttonClickEvent -> {
            this.setEnabled(false);

            groupService.addUserToGroup(group.getId(), user.getId());

            userGrid.getDataProvider().refreshAll();
            this.setEnabled(true);
        });
        return button;
    }

    @Override
    public void close() {
        super.close();
        userGroupsView.getView().getUserGrid().getDataProvider().refreshAll();
    }

    private void configureGridDataProvider() {
        DataProvider dataProvider = DataProvider.fromCallbacks(
                this::fetchUsers,
                query -> {
                    if (group == null) {
                        return 0;
                    }
                    return service.countByGroupNotIn(group);
                }
        );
        userGrid.setDataProvider(dataProvider);
    }

    private Stream fetchUsers(Query<Group, ?> query) {
        if (group == null) {
            return new ArrayList<Group>().stream();
        }
        int offset = query.getOffset();
        int limit = query.getLimit();
        Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                .collect(Collectors.toMap(
                        sort -> sort.getSorted(),
                        sort -> sort.getDirection() == SortDirection.ASCENDING));

        System.out.println(service.findAllByGroupNotIn(offset, limit, group, sortOrder).size());
        return service.findAllByGroupNotIn(offset, limit, group, sortOrder).stream();
    }
}
