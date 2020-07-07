package app.routes.groupedit.components;

import app.constants.Strings;
import app.data.ad.AdServiceImpl;
import app.data.promotion.Promotion;
import app.data.promotion.PromotionRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
@UIScope
@Getter
@Setter
@RequiredArgsConstructor
public class PromotionPlaybackPresenter {
    private final PromotionRepository promotionRepository;

    private final AdServiceImpl adService;

    private PromotionPlaybackView view;

    private Promotion promotion;

    private Binder<Promotion> binder = new Binder<>(Promotion.class);

    private PromotionEditDialog dialog;

    public void view(PromotionPlaybackView view) {
        this.view = view;
        configureBinder();

    }

    public void setPromotionAndRefresh(Long promotionId, Long group) {
        promotion = promotionRepository.findById(promotionId).orElse(new Promotion());

        binder.setBean(promotion);

        if (promotion.getGroupId()==null)
            promotion.setGroupId(group);
        if (promotion.getAdID() != null) {
            dialog.getFileName().setText(adService.getAdById(promotion.getAdID()).getName());
        }
        else
            dialog.getFileName().setText(Strings.AD_NOT_SELECTED);
    }

    private void configureBinder() {
        binder.forField(view.getDurationField()).bind(
                promotion1 -> (double) (promotion1.getDuration()),
                (promotion1, aDouble) -> {
                    promotion1.setDuration(aDouble.intValue());
                });
        binder.forField(view.getPlaybackTimeField()).bind(
                promotion1 -> (double) (promotion1.getMinutes()),
                (promotion1, aDouble) -> promotion1.setMinutes(aDouble.intValue()));
        binder.forField(view.getStartTimePicker()).bind(
                promo -> promo.getStart().toLocalTime(),
                ((promotion1, localTime) -> promotion1.setStart(Time.valueOf(localTime))));
        binder.forField(view.getEndTimePicker()).bind(
                promo -> promo.getStart().toLocalTime(),
                ((promotion1, localTime) -> promotion1.setStart(Time.valueOf(localTime))));
    }

    private boolean validate() {
        boolean result = true;
        view.getStartTimePicker().setInvalid(false);
        view.getEndTimePicker().setInvalid(false);
        view.getDurationField().setInvalid(false);
        view.getPlaybackTimeField().setInvalid(false);

        if (view.getEndTimePicker().getValue().getNano() < view.getStartTimePicker().getValue().getNano()) {
            view.getStartTimePicker().setInvalid(true);
            view.getEndTimePicker().setInvalid(true);
            result = false;
        }

        if (view.getDurationField().getValue().intValue() > 60) {
            view.getDurationField().setInvalid(true);
            result = false;
        }

        if (view.getPlaybackTimeField().getValue() > 300) {
            view.getPlaybackTimeField().setInvalid(true);
            result = false;
        }

        if (promotion.getAdID() == null) {
            Notification.show(Strings.AD_NOT_SELECTED);
            result = false;
        }

        return result;
    }

    public boolean save() {
        if (!validate())
            return false;
        try {
            promotionRepository.save(promotion);
            return true;
        } catch (Exception e) {
            Notification.show(Strings.PROMOTION_NOT_SAVED);
            return false;
        }
    }
}
