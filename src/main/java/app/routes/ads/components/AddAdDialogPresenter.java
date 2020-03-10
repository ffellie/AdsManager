package app.routes.ads.components;


import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@UIScope
@Component
public class AddAdDialogPresenter {
    private AddAdDialog dialog;
    public void view (AddAdDialog dialog){
        this.dialog = dialog;
        configureCancelButton();
        configureSaveButton();
    }
    private void configureCancelButton (){
        dialog.getCancelButton().addClickListener(buttonClickEvent -> dialog.close());
    }
    private void configureSaveButton (){
        dialog.getSaveButton().addClickListener(buttonClickEvent -> {
            dialog.getAdEditView().setEnabled(false);
            dialog.getCancelButton().setEnabled(false);
            dialog.getSaveButton().setEnabled(false);
            if (dialog.getAdEditView().getPresenter().save())
                dialog.close();
            dialog.getAdEditView().setEnabled(true);
            dialog.getCancelButton().setEnabled(true);
            dialog.getSaveButton().setEnabled(true);

        });
    }

}
