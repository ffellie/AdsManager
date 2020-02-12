package app.components.groups;

import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class GroupsPresenter {
    private GroupsView view;
    void view (GroupsView view){
        this.view = view;

    }
}
