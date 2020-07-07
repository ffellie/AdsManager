package app.api.services;

import app.api.models.GroupModel;
import app.api.models.PromotionModel;
import app.data.ad.Ad;
import app.data.ad.AdRepository;
import app.data.group.Group;
import app.data.group.GroupRepository;
import app.data.group.GroupService;
import app.data.promotion.Promotion;
import app.data.promotion.PromotionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupApiServiceImpl implements GroupApiService {
    private final AdRepository adRepository;
    private final GroupRepository groupRepository;
    private final PromotionService promotionService;

    private final GroupService groupService;

    @Override
    public ResponseEntity<GroupModel> getGroup (String url)  {
        System.out.println("Got request");
        List<Group> groups = groupRepository.findByUrl(url);
        if (groups.size()!=1)
            return ResponseEntity.notFound().build();
        Group group = groups.get(0);
        List<Promotion> promotions = promotionService.getByGroup(group.getId());



        return ResponseEntity.ok()
                .body(GroupModel.builder()
                        .name(group.getName())
                        .ads(promotions.stream()
                                .map(promotion -> {
                                    Ad ad = adRepository.getById(promotion.getAdID());
                                    return PromotionModel.builder()
                                            .duration(promotion.getDuration())
                                            .end(promotion.getEnd())
                                            .start(promotion.getStart())
                                            .minutes(promotion.getMinutes())
                                            .url(ad.getFilename())
                                            .mediaType(ad.getMediaType().name())
                                            .build();
                                })
                                .collect(Collectors.toList()))
                        .build());
    }
}
