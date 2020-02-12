package app.components.ads.edit;

import app.components.media.ImageUpload;
import app.components.media.MediaPreviewDialog;
import app.data.Strings;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
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
    private MediaPreviewDialog dialog;
    private ImageUpload imageUpload;
    private AdEditPresenter presenter;
    private TextField nameField;
    private TextArea descriptionField;
    private NumberField durationField;
    private Button saveButton;
    private Button viewMediaButton;
    public AdEditView(AdEditPresenter presenter, MediaPreviewDialog dialog){
        super();
        this.presenter = presenter;
        this.dialog = dialog;
        nameField = new TextField();
        descriptionField = new TextArea();
        durationField = new NumberField();
        saveButton = new Button(Strings.SAVE);
        viewMediaButton = new Button("Просмотр");
        nameField.setLabel("Название");
        durationField.setLabel("Длительность");
        descriptionField.setLabel("Описание");
        add(nameField);
        add(descriptionField);
        add(durationField);
        presenter.view(this);
        add(saveButton);
        add(viewMediaButton);

    }
}
