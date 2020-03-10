package app.routes.groupedit;

import app.routes.MainLayout;
import app.routes.admin.UserEditPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;

@Route(value = "edit-group", layout = MainLayout.class)
@UIScope
@Getter
public class GroupEditRoute extends VerticalLayout implements HasUrlParameter<Long> {
    private long groupID;
    private UserEditView view;
    private UserEditPresenter presenter;

    @Override
    public void setParameter(BeforeEvent event,
                             @OptionalParameter Long param){
        if (param==null) {
            this.groupID = -1;
        }
        else {
            this.groupID = param;
        }
//        presenter.view(view,groupID);
    }
    public GroupEditRoute(UserEditView view, UserEditPresenter presenter){
        super(view);
        this.view=view;
        this.presenter=presenter;
//        view.setWidthFull();
    }



}
