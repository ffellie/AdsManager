package app.login;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("login")
@UIScope
public class LoginRoute extends VerticalLayout {
    public LoginRoute (LoginView view, LoginPresenter presenter) {
        super(view);
        setSizeFull();
        presenter.view(view);
        this.setAlignItems(Alignment.CENTER);
    }
}
