package app.routes.admin;

import app.data.user.User;
import app.data.user.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@UIScope
public class AdminPresenter {
    private UserService service;
    public AdminPresenter(UserService service){
        this.service=service;

    }
    private DataProvider<User,Void> dataProvider;
    void view (AdminView view){
        view.getLogoutButton().addClickListener(clickEvent -> {
            SecurityContextHolder.clearContext();
            UI.getCurrent().navigate("login");
        });
        dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));
                    List<User> persons = service
                            .findAll(offset, limit, sortOrder);

                    return persons.stream();
                },
                query -> service.count());
        if (view.getUserGrid().getColumnByKey("actions")==null)
            view.getUserGrid().addComponentColumn(user -> createButtons(view,user)).setHeader("Действия").setKey("actions");
        view.getFilter().addValueChangeListener(e -> filterUsers(view , e.getValue(), view.getFilterBox().getValue().toString()));
        view.getFilterButton().addClickListener(e -> filterUsers(view, view.getFilter().getValue(), view.getFilterBox().getValue().toString()));
        view.getAddButton().addClickListener(clickEvent -> {UI.getCurrent().navigate("user-edit");});
        filterUsers(view, view.getFilter().getValue(), view.getFilterBox().getValue());
        view.getUserGrid().setDataProvider(dataProvider);
        view.getUserGrid().setPageSize(50);

    }
    private void filterUsers(AdminView view, String param, String filter){
        dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    List<User> userList;
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));
                    userList = service.findByName(offset, limit, param, sortOrder);


                    return userList.stream();
                },
                query -> {
                    return service.countByName(param);
                }
                        );
        view.getUserGrid().setDataProvider(dataProvider);

    }

    private HorizontalLayout createButtons (AdminView view, User user){
        Button editButton = new Button("Редактировать", clickEvent -> {
            UI.getCurrent().navigate("user-edit/" + user.getId() + "/");
        });
        Button deleteButton = new Button("Удалить", clickEvent ->{
            Dialog deleteDialog = new Dialog();
            Button confirmButton = new Button("Да", event -> {
                service.removeUser(user);
                view.getUserGrid().setDataProvider(dataProvider);
                deleteDialog.close();
            });
            Button cancelButton = new Button("Отмена", event -> deleteDialog.close());
            HorizontalLayout hl = new HorizontalLayout(confirmButton, cancelButton);
            deleteDialog.add( new Label("Вы уверены, что хотите удалить данного пользователя?") , hl);
            hl.setAlignSelf(FlexComponent.Alignment.END);
            deleteDialog.open();
        });
        HorizontalLayout buttons = new HorizontalLayout();
        buttons.add(editButton,deleteButton);
        return buttons;
    }

    public DataProvider<User, Void> getDataProvider() {
        return dataProvider;
    }
}
