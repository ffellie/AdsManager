package app.routes.groups;

import app.constants.RouteURLs;
import app.data.user.UserRole;
import app.routes.MainLayout;
import app.security.User;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "groups", layout = MainLayout.class)
@UIScope
@Getter
@Setter
public class GroupsRoute extends VerticalLayout implements  AfterNavigationObserver {
    private GroupsView view;
    public GroupsRoute (GroupsView view){
        super(view);
        setSizeFull();
        this.view = view;
    }



    @Override
    public void afterNavigation(AfterNavigationEvent event){
        view.getPresenter().configureGridData();
    }
}
