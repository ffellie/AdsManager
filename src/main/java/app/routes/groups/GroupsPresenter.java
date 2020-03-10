package app.routes.groups;

import app.ConstData;
import app.data.Strings;
import app.data.group.PromotionRepository;
import app.data.user.UserRole;
import app.routes.ads.AdsPageBinder;
import app.data.group.Group;
import app.data.group.GroupService;
import app.data.user.User;
import app.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
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
    private final UserService userService;
    private final PromotionRepository promotionRepository;
    private User user;
    void view (GroupsView view){
        this.view = view;
        getUser();
        addColumns();
    }

    private void addColumns (){
        view.getGroupsGrid().addColumn(promotionRepository::countAllByGroup).setHeader(Strings.NUM_OF_ADS).setKey("ads-num");
        addActionButtons();
    }
    private void addActionButtons (){
        view.getGroupsGrid().addComponentColumn(this::createSelectButton).setHeader(Strings.ACTIONS);
    }
    private Button createSelectButton (Group g){
        Button selectButton = new Button(Strings.VIEW);
        selectButton.addClickListener(event ->{
            UI.getCurrent().navigate(ConstData.GROUP_EDIT_ROUTE + g.getId());
        });
        return selectButton;
    }

    private void getUser(){
        app.security.User secUser = (app.security.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userService.getUserByName(secUser.getName());
    }

    public void configureGridData (){
        view.getGroupsGrid().setPageSize(50);
        if (user.getRole()== UserRole.ADMIN) {
            DataProvider<Group, Void> dataProvider = DataProvider.fromCallbacks(
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
        else {
            List<Long> id = new ArrayList<>();
            id.add(user.getId());
            view.getGroupsGrid().setItems(service.findDistinctByUserIDsIn(id));
        }
    }
}
