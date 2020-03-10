package app.routes.groupedit.components;

import app.data.group.Promotion;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.time.LocalTime;

@Component
@UIScope
@Getter
@Setter
public class PromotionPlaybackPresenter {
    private PromotionPlaybackView view;
    private Promotion promotion;
    private Binder<Promotion> binder= new Binder<>(Promotion.class);
    public void view (PromotionPlaybackView view){
        this.view = view;
        configureBinder();
    }

    private void configureBinder(){
        binder.forField(view.getDurationField()).bind(
                promotion1 -> (double)(promotion1.getDuration()),
                (promotion1, aDouble) -> {promotion1.setDuration(aDouble.intValue());});
        binder.forField(view.getPlaybackTimeField()).bind(
                promotion1 -> (double)(promotion1.getMinutes()),
                (promotion1, aDouble) -> promotion1.setMinutes(aDouble.intValue()));
        binder.forField(view.getStartTimePicker()).bind(
                promo -> promo.getStart().toLocalTime(),
                ((promotion1, localTime) -> promotion1.setStart(Time.valueOf(localTime))));
        binder.forField(view.getEndTimePicker()).bind(
                promo -> promo.getStart().toLocalTime(),
                ((promotion1, localTime) -> promotion1.setStart(Time.valueOf(localTime))));
    }

    private boolean validate (){
        view.getStartTimePicker().setInvalid(false);
        view.getEndTimePicker().setInvalid(false);
        view.getDurationField().setInvalid(false);
        view.getPlaybackTimeField().setInvalid(false);

        if (view.getEndTimePicker().getValue().getNano()<view.getStartTimePicker().getValue().getNano()){
            view.getStartTimePicker().setInvalid(true);
            view.getEndTimePicker().setInvalid(true);
        }
    }
}
