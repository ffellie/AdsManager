package app.routes.usergroups.components;

import app.data.group.Group;
import app.data.user.User;
import app.data.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Stream;

@Component
@UIScope
public class AddUserToGroupDialog extends Dialog {
    private UserService service;
    private Grid<User> userGrid;
    private Group group;
    public AddUserToGroupDialog (UserService service){
        super();
        this.service=service;
        userGrid = new Grid<>(User.class);
        userGrid.removeAllColumns();
        userGrid.addColumns("name","role");
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
        userGrid.getDataProvider().refreshAll();
    }



    private Button createAddButton (User user){
        Button button = new Button("Добавить");
        button.addClickListener(buttonClickEvent -> {
           this.setEnabled(false);
           group.getUserIDs().add(user.getId());
           userGrid.getDataProvider().refreshAll();
           this.setEnabled(true);
            userGrid.getDataProvider().refreshAll();
        });
        return button;
    }

    private void configureGridDataProvider (){
        DataProvider dataProvider = DataProvider.fromCallbacks(
                this::fetchUsers,
                query -> {
                    if (group==null)
                        return 0;
                    return service.countByGroupNotIn(group);
                }
        );
        userGrid.setDataProvider(dataProvider);
    }

    private Stream fetchUsers(Query<Group, ?> query) {
        if (group==null)
            return new ArrayList<Group>().stream();
        return service.findAllByGroupNotIn(group).stream();
    }
}
