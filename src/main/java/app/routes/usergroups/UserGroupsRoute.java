package app.routes.usergroups;

import app.routes.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "usergroups", layout = MainLayout.class)
@UIScope
public class UserGroupsRoute extends VerticalLayout {

    public UserGroupsRoute (UserGroupsView view){
        super(view);
    }
}
