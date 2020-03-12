package app.routes.groupedit.components;

import app.constants.Strings;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@UIScope
@Getter
public class PromotionPlaybackView  extends VerticalLayout {
    private PromotionPlaybackPresenter presenter;
    private TimePicker startTimePicker;
    private TimePicker endTimePicker;
//    private DatePicker startDatePicker;
//    private DatePicker endDatePicker;
    private NumberField durationField, playbackTimeField;
    public PromotionPlaybackView (PromotionPlaybackPresenter presenter){
        this.presenter = presenter;
        startTimePicker = new TimePicker(Strings.FROM);
        endTimePicker = new TimePicker(Strings.TO);
        durationField = new NumberField(Strings.DURATION);
        playbackTimeField = new NumberField(Strings.PLAYBACK_TIME);
        startTimePicker.setErrorMessage(Strings.TIME_ERROR);
        durationField.setMin(5);
        durationField.setMax(300);
        playbackTimeField.setMin(0);
        playbackTimeField.setMax(60);
        FormLayout form = new FormLayout(startTimePicker,endTimePicker,durationField,playbackTimeField);
        add(form);
        presenter.view(this);
    }
}
