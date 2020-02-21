package app.routes.admin;

import app.routes.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = "user-edit", layout = MainLayout.class)
@UIScope
public class UserEditRoute  extends VerticalLayout implements HasUrlParameter<Long> {
    private long userID;
    private UserEditView view;
    private UserEditPresenter presenter;

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long param){
        if (param==null) {
            this.userID = -1;
        }
        else {
            this.userID = param;
        }
        presenter.view(view,userID);
    }
    public UserEditRoute (UserEditView view, UserEditPresenter presenter){
        super(view);
        this.view=view;
        this.presenter=presenter;
//        view.setWidthFull();
    }



    public long getUserID() {
        return userID;
    }
}
