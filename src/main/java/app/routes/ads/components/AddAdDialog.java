package app.routes.ads.components;

import app.constants.Strings;
import app.routes.ads.components.edit.AdEditView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
@Setter
public class AddAdDialog extends Dialog {
    private AdEditView adEditView;
    private AddAdDialogPresenter presenter;
    private Button cancelButton = new Button(Strings.CANCEL), saveButton = new Button(Strings.SAVE);
    public AddAdDialog (AdEditView adEditView,AddAdDialogPresenter presenter){
        super();
        this.adEditView = adEditView;
        this.presenter = presenter;
        setHeight("60%");
        setMinHeight("500px");
        setMaxWidth("300px");
        add(adEditView, new HorizontalLayout(cancelButton,saveButton));
        presenter.view(this);
    }
    @Override
    public void open(){
        super.open();

    }

}
