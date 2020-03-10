package app.routes.ads;

import app.routes.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route(value = "ads", layout = MainLayout.class)
@UIScope
public class AdsRoute  extends VerticalLayout {
    private AdsView view;
    public AdsRoute (AdsView view){
        super(view);
    }
}
