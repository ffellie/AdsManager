package app.data.user;

import app.data.ad.AdRepository;
import app.data.group.Group;
import app.data.group.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AdRepository adRepository;
    private final PromotionRepository promotionRepository;

    public void removeUser (User user){
        List<Long> adIDs = new ArrayList<>();
        adRepository.findAllByUser(user).forEach(ad -> {adIDs.add(ad.getId());});
        promotionRepository.deleteAllByAdIDIn(adIDs);
        adRepository.deleteAll(user.getAds());
    }



    public List<User> findAll(int offset, int limit, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        List<User> items = userRepository.findAll(pageRequest).getContent();
        return items;
    }
    public List<User> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));

        return userRepository.findByNameContains(name, pageRequest);
    }
    public User getUserByName(String name){
        Optional<User> result = userRepository.findByName(name);
        return result.orElse(null);
    }
    public Integer count() {
        return Math.toIntExact(userRepository.count());
    }
    public Integer countByName(String name) {
        return Math.toIntExact(userRepository.countByNameContains(name));
    }

    public List<User> findAllByGroup (int offset, int limit, Group group, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));

        return userRepository.findAllByIdIn(group.getUserIDs(), pageRequest);
    }
    public List<User> findAllByGroupNotIn (int offset, int limit, Group group, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));

        Collection<Long> IDs ;
        if (group.getUserIDs().size()==0) {
            IDs = new ArrayList<>();
            IDs.add(Long.valueOf(-1));
        }
        else IDs = group.getUserIDs();
        return userRepository.findAllByIdNotIn(IDs, pageRequest);
    }

    public Integer countByGroupNotIn (Group group){
        Collection<Long> IDs;
        if (group.getUserIDs().size()==0) {
            IDs = new ArrayList<>();
            IDs.add(Long.valueOf(-1));
        }
        else IDs = group.getUserIDs();
        return userRepository.countAllByIdNotIn(IDs);
    }
    public Integer countByGroup (Group group){
        return userRepository.countAllByIdIn(group.getUserIDs());
    }

}
