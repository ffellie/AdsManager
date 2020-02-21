package app.routes.admin;

import app.data.user.User;
import app.data.user.UserRepository;
import app.data.user.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@UIScope
@Getter
@Setter
public class AdminView extends VerticalLayout {
    private UserRepository userRepository;
    private UserService service;
    private TextField filter;
    private Button filterButton;
    private ComboBox<String> filterBox;
    private Grid<User> userGrid;
    private Button addButton;
    private Button logoutButton;

    public AdminView (UserRepository userRepository, UserService service){
        this.service=service;
        logoutButton = new Button("Выйти");
        this.userRepository=userRepository;
        this.userGrid = new Grid<User>(User.class);
        this.filter = new TextField();
        filterBox = new ComboBox<>();
        filterButton = new Button("Поиск");
        filterBox.setItems("Имени", "Электронному адресу");
        filterBox.setValue("Электронному адресу");
        filterBox.setAllowCustomValue(false);
        filterBox.setLabel("Поиск по:");
        filter.setLabel("Поиск пользователя:");
        addButton = new Button("Добавить пользователя");
        HorizontalLayout filterLayout = new HorizontalLayout();
        HorizontalLayout addButtonWrapper = new HorizontalLayout(addButton, logoutButton);
        HorizontalLayout wrapper = new HorizontalLayout(filterLayout, addButtonWrapper);
        wrapper.setAlignItems(Alignment.BASELINE);
        wrapper.setWidth("100%");
        addButtonWrapper.setSizeUndefined();
        wrapper.setAlignItems(Alignment.END);
        filterLayout.setAlignItems(Alignment.BASELINE);
        filterLayout.add(filter, filterBox, filterButton);
        userGrid.removeColumnByKey("id");
        userGrid.setColumns("name", "password", "role");
        userGrid.getColumnByKey("name").setHeader("Имя пользователя");
        userGrid.getColumnByKey("password").setHeader("Пароль");
        userGrid.getColumnByKey("role").setHeader("Отдел");
        userGrid.setSizeUndefined();
        userGrid.setHeight("600px");
        userGrid.setMaxHeight("80%");
        add(wrapper,userGrid);
    }

}
