package app.routes.admin;

import app.data.user.UserRole;
import app.routes.MainLayout;
import app.security.User;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "admin", layout = MainLayout.class)
@UIScope
public class AdminRoute extends VerticalLayout implements BeforeEnterObserver, AfterNavigationObserver {
    private AdminView view;
    private AdminPresenter presenter;
    public AdminRoute(AdminView view,AdminPresenter presenter){
        super(view);
        this.view=view;
        this.presenter=presenter;
        presenter.view(view);
//        view.setWidthFull();

    }
    @Override
    public void beforeEnter(BeforeEnterEvent event){
        if ((SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            event.rerouteTo("login");
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
        presenter.getDataProvider().refreshAll();
    }
}
