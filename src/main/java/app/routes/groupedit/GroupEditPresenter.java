package app.routes.groupedit;

import app.constants.Strings;
import app.data.ad.Ad;
import app.data.ad.AdService;
import app.data.ad.AdServiceImpl;
import app.data.ad.MediaType;
import app.data.group.*;
import app.data.promotion.Promotion;
import app.data.promotion.PromotionRepository;
import app.data.promotion.PromotionService;
import app.routes.groupedit.components.PromotionEditDialog;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@UIScope
@RequiredArgsConstructor
public class GroupEditPresenter {
    private Long group;

    private GroupEditView view;

    private final PromotionRepository promotionRepository;

    private final AdServiceImpl adService;

    private final GroupService groupService;

    private final PromotionEditDialog promotionEditDialog;

    private final PromotionService service;

    public void view(GroupEditView view) {
        this.view = view;
        promotionEditDialog.getFilePicker().setGroupEditPresenter(this);
        configureBackButton();
        addGridColumns();
        configureAddButton();
    }

    public void load(Long group) {
        this.group = group;
        refreshGrid();
    }

    private void addGridColumns() {
        view.getPromotionGrid().addColumn(promotion -> {
            return adService.getAdById(promotion.getAdID()).getName();
        }).setHeader(Strings.NAME).setKey("name");
        view.getPromotionGrid().getColumnByKey("start").setHeader(Strings.PROMOTION_START_TIME);
        view.getPromotionGrid().getColumnByKey("end").setHeader(Strings.PROMOTION_END_TIME);
        view.getPromotionGrid().getColumnByKey("minutes").setHeader(Strings.PROMOTION_MINUTES);
        view.getPromotionGrid().getColumnByKey("duration").setHeader(Strings.PROMOTION_DURATION);
        view.getPromotionGrid().addComponentColumn(this::createGridButtons).setHeader(Strings.ACTIONS).setKey("actions");
    }

    private void configureAddButton (){
        view.getAddButton().addClickListener(buttonClickEvent -> {
            promotionEditDialog.open((long)-1, group);
        });
    }

    private void configureBackButton(){
        view.getBackButton().addClickListener(click -> UI.getCurrent().navigate("groups"));
    }

    private HorizontalLayout createGridButtons(Promotion promotion) {
        Ad ad = adService.getAdById(promotion.getAdID());

        Button viewButton = new Button(Strings.VIEW);
        viewButton.addClickListener(event -> {
            if (ad.getMediaType() == MediaType.Picture) {
                view.getMediaPreviewDialog().updatePoster(ad.getFilename());
                view.getMediaPreviewDialog().toggleImage();
            } else {
                view.getMediaPreviewDialog().updateVideo(ad.getFilename());
                view.getMediaPreviewDialog().toggleVideo();
            }
            view.getMediaPreviewDialog().open();
        });

        Button removeButton = new Button();

        removeButton.addClickListener(buttonClickEvent -> {
            Dialog dialog = new Dialog();
            Text text = new Text(Strings.ARE_U_SURE_PROMOTION);
            Button cancelButton = new Button(Strings.CANCEL), yesButton = new Button(Strings.YES);

            cancelButton.addClickListener(buttonClickEvent1 -> dialog.close());

            yesButton.addClickListener(buttonClickEvent1 -> {
                        view.setEnabled(false);
                        promotionRepository.delete(promotion);
                        view.getPromotionGrid().getDataProvider().refreshAll();
                        view.setEnabled(true);
                        dialog.close();
                    }
            );

            dialog.add(new VerticalLayout(text, new HorizontalLayout(cancelButton, yesButton)));
            dialog.setWidth("400px");
            dialog.setHeight("100px");
            dialog.open();
        });
        Button editPromotion = new Button(Strings.PLAYBACK_OPTIONS);
        editPromotion.addClickListener(buttonClickEvent -> {
            promotionEditDialog.open(promotion.getId(),group);
        });
        return new HorizontalLayout(viewButton, editPromotion, removeButton);
    }

    public void configureGridData() {
        view.getPromotionGrid().setPageSize(50);

        DataProvider<Promotion, Void> dataProvider = DataProvider.fromCallbacks(
                query -> {
                    int offset = query.getOffset();
                    int limit = query.getLimit();
                    Map<String, Boolean> sortOrder = query.getSortOrders().stream()
                            .collect(Collectors.toMap(
                                    sort -> sort.getSorted(),
                                    sort -> sort.getDirection() == SortDirection.ASCENDING));

                    return service.findByGroup(offset, limit, group, sortOrder).stream();
                },
                query -> service.countByGroup(group));

        view.getPromotionGrid().setDataProvider(dataProvider);
        view.getPromotionGrid().getDataProvider().refreshAll();
    }

    public void refreshGrid (){
        view.getPromotionGrid().setItems(service.getByGroup(group));
    }
}
