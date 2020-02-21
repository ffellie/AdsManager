package app.routes.admin;

import app.data.user.User;
import app.data.user.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@UIScope
public class UserEditPresenter {
    @Autowired
    private UserRepository userRepository;
    private User user;
    void view(UserEditView view, long userID) {
        view.getCancelButton().addClickListener(clickEvent -> UI.getCurrent().navigate("admin"));
        view.getUserRoleBox().setInvalid(false);
        view.getPasswordText().setInvalid(false);
        view.getConfPasswordText().setInvalid(false);
        view.getNameText().setInvalid(false);

        if (userID == -1) {
            user = new User();
            view.getNameText().setValue("");
            view.getPasswordText().setValue("");
            view.getConfPasswordText().setValue("");
        } else {
            Optional<User> user1 = userRepository.findById(userID);
            if (user1.isPresent()) {
                user = user1.get();
                view.getNameText().setValue(user.getName());
                view.getPasswordText().setValue(user.getPassword());
                view.getConfPasswordText().setValue(user.getPassword());
                view.getUserRoleBox().setValue(user.getRole());
            }
        }

        view.getSaveButton().addClickListener(clickEvent -> {
            if (validateFields(view)) {
                user.setName(view.getNameText().getValue());
                user.setPassword(view.getPasswordText().getValue());
                user.setRole(view.getUserRoleBox().getValue());
                try {
                    userRepository.save(user);
                } catch (Exception e) {
                }
                UI.getCurrent().navigate("admin");
            }
        });
    }
    private boolean validateFields (UserEditView view) {
        view.getUserRoleBox().setInvalid(false);
        view.getPasswordText().setInvalid(false);
        view.getConfPasswordText().setInvalid(false);
        view.getNameText().setInvalid(false);
        boolean result = true;
        if (view.getNameText().getValue().length() >= 100) {
            view.getNameText().setErrorMessage("Превышено допустимое количество символов.");
            view.getNameText().setInvalid(true);
            result = false;
        }
        if (view.getNameText().getValue().equals("")) {
            view.getNameText().setErrorMessage("Вы должны указать имя.");
            view.getNameText().setInvalid(true);
            result = false;
        }
        if (!view.getPasswordText().getValue().equals(view.getConfPasswordText().getValue())) {
            view.getPasswordText().setErrorMessage("Пароли должны совпадать.");
            view.getPasswordText().setInvalid(true);
            view.getConfPasswordText().setInvalid(true);
            result = false;
        }
        if (view.getPasswordText().getValue().equals("")) {
            view.getPasswordText().setErrorMessage("Вы должны указать пароль.");
            view.getPasswordText().setInvalid(true);
            result = false;
        }
        Optional<User> user1 = userRepository.findByName(view.getNameText().getValue());
        if (user1.isPresent() && user1.get().getId() != user.getId()) {
            view.getNameText().setErrorMessage("Пользователь с таким именем уже существует.");
            view.getNameText().setInvalid(true);
            result = false;
        }
        if (view.getUserRoleBox().getValue()==null) {
            view.getUserRoleBox().setErrorMessage("Вы должны выбрать должность.");
            view.getUserRoleBox().setInvalid(true);
            result = false;
        }
        return result;
    }
}
