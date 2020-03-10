package app.routes;


import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;

@Route("")
@UIScope
public class MainLayout extends AppLayout implements RouterLayout {
    @Getter
    private Tab kioskTab, adminTab;
    public MainLayout (){
        setPrimarySection(AppLayout.Section.DRAWER);
        Image img = new Image("https://i.imgur.com/GPpnszs.png", "Vaadin Logo");
        img.setHeight("44px");
        kioskTab = new Tab("Группы");
        adminTab = new Tab("Администрирование");
        Tabs tabs = new Tabs(kioskTab, adminTab);
        tabs.setOrientation(Tabs.Orientation.HORIZONTAL);
        tabs.getElement().getStyle().set("margin-left", "40%");
        addToNavbar(tabs);

        tabs.addSelectedChangeListener(new ComponentEventListener<Tabs.SelectedChangeEvent>() {
            @Override
            public void onComponentEvent(Tabs.SelectedChangeEvent selectedChangeEvent) {
                if(selectedChangeEvent.getSelectedTab()==kioskTab)
                    getUI().ifPresent(ui -> ui.navigate("main"));
                else if(selectedChangeEvent.getSelectedTab()==adminTab)
                    getUI().ifPresent(ui -> ui.navigate("admin"));
            }
        });
    }
}
