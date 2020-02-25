package app.components.groups;

import app.components.ads.AdsPageBinder;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.User;
import app.data.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@UIScope
@RequiredArgsConstructor
public class GroupsPresenter {
    private GroupsView view;
    private final GroupService service;
    private final AdsPageBinder parentPresenter;
    private final UserService userService;
    private Group group;

    void view (GroupsView view){
        this.view = view;
        configureGridData();
        configureAddButton();
        configureSaveButton();
        configureGridSelectionEvent();

        group = new Group();
        group.setAdIDs(new HashSet<>());
        group.setUrl(UUID.randomUUID().toString());
        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByName(secUser.getName());
    }

    private void configureAddButton (){
        view.getAddButton().addClickListener(click ->{
            group = new Group();
            group.setAdIDs(new HashSet<>());
            group.setUrl(UUID.randomUUID().toString());
            view.getGroupName().clear();
            parentPresenter.onAddGroup();
        });
    }

    private void configureSaveButton (){
        view.getSaveButton().addClickListener(buttonClickEvent -> {
            System.out.println("Save");
            if (group!=null && !view.getGroupName().isEmpty()) {
                group.setName(view.getGroupName().getValue());
                service.saveGroup(group);
                view.getGroupName().clear();
                app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User user = userService.getUserByName(secUser.getName());
                view.getGroupsGrid().getDataProvider().refreshAll();
            }
        });
    }

    private void addActionButtons (){
        view.getGroupsGrid().addComponentColumn(this::createSelectButton);
    }
    private Button createSelectButton (Group g){
        Button selectButton = new Button("Просмотр");
        selectButton.addClickListener(event ->{
            group = g;
            view.getGroupName().clear();
            parentPresenter.onGroupSelected(group.getId());
        });
        return selectButton;
    }

    private void configureGridSelectionEvent (){
        view.getGroupsGrid().asSingleSelect().addValueChangeListener(event ->{
            group = event.getValue();
            view.getGroupName().clear();
            parentPresenter.onGroupSelected(group.getId());
        });
    }
    private void configureGridData (){
        view.getGroupsGrid().setPageSize(50);
        DataProvider<Group,Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));

                    List<Group> persons = service
                            .findAll(offset, limit, sortOrder);

                    return persons.stream();
                },
                query -> service.count());
        dataProvider.refreshAll();

        view.getGroupsGrid().setDataProvider(dataProvider);
    }
}
