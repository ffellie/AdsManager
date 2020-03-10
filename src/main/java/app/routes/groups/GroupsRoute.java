package app.routes.groups;

import app.routes.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;

@Route(value = "groups", layout = MainLayout.class)
@UIScope
@Getter
@Setter
public class GroupsRoute extends VerticalLayout {
    private GroupsView view;
    public GroupsRoute (GroupsView view){
        super(view);
        this.view = view;
    }
}
