package app.routes.ads.components.edit;

import app.components.media.ImageUpload;
import app.constants.Strings;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


//@Route(value = "upload", layout = AdsView.class)
@Component
@UIScope
@Getter
@Setter
public class AdEditView extends VerticalLayout {
    private ImageUpload imageUpload;
    private AdEditPresenter presenter;
    private TextField nameField;
    private TextArea descriptionField;
    private Text header;
    public AdEditView(AdEditPresenter presenter){
        super();
        this.presenter = presenter;
        nameField = new TextField();
        descriptionField = new TextArea();
        nameField.setLabel("Название");
        header = new Text(Strings.ADD_NEW_FILE);
        descriptionField.setLabel("Описание");
        add(nameField);
        add(descriptionField);
        presenter.view(this);
    }
}
