package app.routes.groupedit;

import app.constants.RouteURLs;
import app.data.group.Group;
import app.data.user.UserRole;
import app.routes.MainLayout;
import app.routes.admin.UserEditPresenter;
import app.security.User;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

@Route(value = "group-edit", layout = MainLayout.class)
@UIScope
@Getter
public class GroupEditRoute extends VerticalLayout implements HasUrlParameter<Long> {
    private long groupID;
    private GroupEditView view;
    private GroupEditPresenter presenter;

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long param){
        if ((SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)){
            event.rerouteTo(RouteURLs.LOGIN_ROUTE);
        }
        else {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (user.getRole() != UserRole.ADMIN) {
                event.forwardTo("");
            }
        }

        if (param==null) {
            this.groupID = -1;
        }
        else {
            this.groupID = param;
        }
        presenter.load(groupID);
    }
    public GroupEditRoute(GroupEditView view, GroupEditPresenter presenter){
        super(view);
        this.view=view;
        this.presenter=presenter;
//        view.setWidthFull();
    }
}
