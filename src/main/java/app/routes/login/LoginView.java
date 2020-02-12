package app.routes.login;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class LoginView extends LoginForm {
    public LoginView(){
        LoginI18n i18n= new LoginI18n();
        LoginI18n.Form i18nForm = new LoginI18n.Form();
        i18nForm.setSubmit("Вход");
        i18nForm.setUsername("Логин");
        i18nForm.setPassword("Пароль");
        i18nForm.setTitle("Вход");
        i18n.setForm(i18nForm);
        LoginI18n.ErrorMessage errorMessage = new LoginI18n.ErrorMessage();
        errorMessage.setMessage("Неверный логин или пароль");
        i18n.setErrorMessage(errorMessage);
        setI18n(i18n);
        setForgotPasswordButtonVisible(false);
        this.getElement().getStyle().set("margin-top","10%");
    }
}