package app.data.ad;

import app.data.promotion.PromotionRepository;
import app.data.group.groupAd.GroupAdRepository;
import app.data.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdServiceImpl {
    private final AdRepository repository;

    private final GroupAdRepository groupAdRepository;

    private final PromotionRepository promotionRepository;

    public Ad getAdById(long adID) {
        return repository.getById(adID);
    }

    public void saveAd(Ad ad) {
        repository.save(ad);
    }

    public List<Ad> findAll() {
        return repository.findAll();
    }

    public List<Ad> findAll(int offset, int limit, Map<String, Boolean> sortOrders, User user) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        return repository.findAllByUser(user, pageRequest);
    }

    public Integer count(User user) {
        return Math.toIntExact(repository.countAllByUser(user));
    }

    public List<Ad> findByUserAndName(int offset, int limit, User user, String name, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        List<Ad> items = repository.findByUserAndNameContains(user, name, pageRequest);
        return items;
    }

    public Integer countByUserAndName(User user, String name) {
        return Math.toIntExact(repository.countAllByUserAndNameContains(user, name));
    }

    public Set<Ad> getAllByID(Collection<Long> adIDs) {
        return repository.findAllByIdIn(adIDs);
    }

    @Transactional
    public void remove(List<Ad> ads) {
        groupAdRepository.deleteAllByAdIn(ads.stream()
                .map(Ad::getId)
                .collect(Collectors.toList()));
        promotionRepository.deleteAllByAdIDIn(ads
                .stream()
                .map(Ad::getId)
                .collect(Collectors.toList()));
        repository.deleteAll(ads);
    }
}
