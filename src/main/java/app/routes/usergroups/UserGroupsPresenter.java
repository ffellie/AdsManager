package app.routes.usergroups;


import app.data.Strings;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@UIScope
@RequiredArgsConstructor
public class UserGroupsPresenter {
    private UserGroupsView view;
    private Group selectedGroup;
    private final UserService userService;
    private final GroupService groupService;
    public void view (UserGroupsView view){
        this.view=view;
        configureGroupsGridDataProvider();
        configureUserGridDataProvider();
        configureGroupsGridColumns();
        configureButtonClickEvents();
    }

    private void configureGroupsGridColumns (){
        view.getUserGroupGrid().addComponentColumn(this::createButtonsForGroupsGrid);
    }

    private HorizontalLayout createButtonsForGroupsGrid (Group group){
        HorizontalLayout layout = new HorizontalLayout();
        Button viewButton = new Button(Strings.VIEW_GROUP);
        viewButton.addClickListener(click ->{
            selectedGroup = group;
            view.getUserGroupGrid().getDataProvider().refreshAll();
        });
        layout.add(viewButton);
        return layout;
    }

    private void configureButtonClickEvents (){
        view.getAddUserButton().addClickListener(buttonClickEvent -> {
            view.getAddUserToGroupDialog().open(selectedGroup);
        });
    }

    private void configureGroupsGridDataProvider (){
        DataProvider dataProvider = DataProvider.fromCallbacks(
                this::fetchGroups,
                query -> {
                     return groupService.count();
                }
        );
        view.getUserGroupGrid().setDataProvider(dataProvider);
    }

    private void configureUserGridDataProvider (){
        DataProvider dataProvider = DataProvider.fromCallbacks(
                this::fetchUsers,
                query -> {
                    if (selectedGroup==null)
                        return 0;
                    return userService.countByGroup(selectedGroup);
                }
        );
        view.getUserGrid().setDataProvider(dataProvider);
    }

    private Stream fetchGroups(Query<Group, ?> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        List itemList;
        Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                .collect(Collectors.toMap(
                        SortOrder::getSorted,
                        sort -> sort.getDirection() == SortDirection.ASCENDING));

        itemList =
                groupService.findAll(offset, limit, sortOrder);

        return itemList.stream();
    }

    private Stream fetchUsers(Query<Group, ?> query) {
        if (selectedGroup==null)
            return new ArrayList<Group>().stream();
        return userService.findAllByGroup(selectedGroup).stream();
    }
}
