package app.routes.groups;

import app.ConstData;
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
public class GroupsRoute extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private GroupsView view;
    public GroupsRoute (GroupsView view){
        super(view);
        setSizeFull();
        this.view = view;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event){
        if ((SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            event.rerouteTo(ConstData.LOGIN_ROUTE);
        }
        else {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getRole() != UserRole.ADMIN) {
                event.forwardTo("");
            }
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event){
        view.getPresenter().configureGridData();
    }
}
