package app.routes;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;

@Route("main")
@UIScope
public class AdsRoute  extends VerticalLayout {
    private AdsView view;
    public AdsRoute (AdsView view){
        super(view);
    }
}
