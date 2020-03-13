package app.routes.usergroups;


import app.constants.Strings;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.User;
import app.data.user.UserService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.provider.SortOrder;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@UIScope
@RequiredArgsConstructor
@Getter
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
        configureAddGroupButton();
        configureUserGridColumns();
        view.getAddUserButton().setEnabled(false);
    }

    private void configureGroupsGridColumns (){
        view.getUserGroupGrid().addComponentColumn(this::createButtonsForGroupsGrid).setHeader(Strings.ACTIONS).setKey("actions").setWidth("300px");
    }
    private void configureUserGridColumns (){
        view.getUserGrid().addComponentColumn(this::createButtonsForUserGrid).setHeader(Strings.ACTIONS);
    }
    private HorizontalLayout createButtonsForUserGrid(User user){
        Button delete = new Button(Strings.REMOVE);
        delete.addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            Text text = new Text(Strings.ARE_U_SURE_USER);
            Button cancelButton = new Button(Strings.CANCEL), yesButton = new Button(Strings.YES);
            cancelButton.addClickListener(buttonClickEvent1 -> dialog.close());
            yesButton.addClickListener(buttonClickEvent1 -> {
                        view.setEnabled(false);
                        groupService.removeUserFromGroup(selectedGroup,user);
                        view.getUserGrid().getDataProvider().refreshAll();
                        view.setEnabled(true);
                        dialog.close();
                    }
            );
            dialog.add(new VerticalLayout(text,new HorizontalLayout(cancelButton,yesButton)));
            dialog.setWidth("400px");
            dialog.setHeight("220px");
            dialog.open();
        });
        return new HorizontalLayout(delete);
    }

    private void configureAddGroupButton (){
        view.getAddGroupButton().addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            Text text = new Text(Strings.PROVIDE_NAME_FOR_GROUP);
            TextField name = new TextField(Strings.NAME);
            Button cancelButton = new Button(Strings.CANCEL), yesButton = new Button(Strings.SAVE);
            cancelButton.addClickListener(buttonClickEvent1 -> dialog.close());
            yesButton.addClickListener(buttonClickEvent1 -> {
                        if (name.getValue()!=null && !name.getValue().trim().isEmpty() ) {
                            view.setEnabled(false);
                            Group group= new Group();
                            group.setName(name.getValue());
                            group.setUrl(UUID.randomUUID().toString());
                            groupService.saveGroup(group);
                            view.getUserGroupGrid().getDataProvider().refreshAll();
                            view.setEnabled(true);
                        }
                        else{
                            name.setErrorMessage(Strings.FIELD_CANNOT_BE_EMPTY);
                            name.setInvalid(true);
                        }
                    }
            );
            dialog.add(new VerticalLayout(text,name,new HorizontalLayout(cancelButton,yesButton)));
            dialog.setWidth("400px");
            dialog.setHeight("200px");
            dialog.open();
        });
    }

    private HorizontalLayout createButtonsForGroupsGrid (Group group){
        HorizontalLayout layout = new HorizontalLayout();
        Button viewButton = new Button(Strings.VIEW_GROUP);
        Button deleteButton = new Button(Strings.DELETE);
        viewButton.addClickListener(click ->{
            selectedGroup = group;
            configureUserGridDataProvider();
            view.getUserGroupGrid().getDataProvider().refreshAll();
            view.getAddUserButton().setEnabled(true);
        });
        deleteButton.addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            Text text = new Text(Strings.ARE_U_SURE_GROUP);
            Button cancelButton = new Button(Strings.CANCEL), yesButton = new Button(Strings.YES);
            cancelButton.addClickListener(buttonClickEvent1 -> dialog.close());
            yesButton.addClickListener(buttonClickEvent1 -> {
                view.setEnabled(false);
                groupService.remove(group);
                view.getUserGroupGrid().getDataProvider().refreshAll();
                view.setEnabled(true);
                dialog.close();
                }
            );
            dialog.add(new VerticalLayout(text,new HorizontalLayout(cancelButton,yesButton)));
            dialog.setWidth("400px");
            dialog.setHeight("100px");
            dialog.open();
        });
        layout.add(viewButton, deleteButton);
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
        int offset = query.getOffset();
        int limit = query.getLimit();
        Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                .collect(Collectors.toMap(
                        SortOrder::getSorted,
                        sort -> sort.getDirection() == SortDirection.ASCENDING));

        return userService.findAllByGroup(offset, limit, selectedGroup, sortOrder).stream();
    }
}
