package app.routes.admin;

import app.data.user.UserRole;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
@Setter
public class UserEditView extends FormLayout {
    private TextField nameText;
    private PasswordField passwordText;
    private PasswordField confPasswordText;
    private ComboBox<UserRole> userRoleBox;
    private Button saveButton;
    private Button cancelButton;
    public UserEditView(){
        nameText = new TextField();
        passwordText=new PasswordField();
        confPasswordText=new PasswordField();
        userRoleBox=new ComboBox<UserRole>();
        userRoleBox.setItems(UserRole.values());
        saveButton=new Button("Сохранить");
        cancelButton=new Button("Отмена");
        HorizontalLayout buttons = new HorizontalLayout(saveButton,cancelButton);
        buttons.setAlignItems(FlexComponent.Alignment.BASELINE);
        addFormItem(nameText,"Имя:");
        addFormItem(passwordText,"Пароль:");
        addFormItem(confPasswordText, "Подтверждение пароля:");
        addFormItem(userRoleBox, "Отдел:");
        add(buttons);
    }

}
