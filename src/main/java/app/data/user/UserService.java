package app.data.user;

import app.data.ad.Ad;
import app.data.ad.AdRepository;
import app.data.ad.AdServiceImpl;
import app.data.group.Group;
import app.data.promotion.PromotionRepository;
import app.data.group.groupUser.GroupUser;
import app.data.group.groupUser.GroupUserRepository;
import lombok.RequiredArgsConstructor;
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

    private final AdServiceImpl adService;

    private final PromotionRepository promotionRepository;

    private final GroupUserRepository groupUserRepository;

    @Transactional
    public void removeUser(User user) {
        List<Ad> ads = adRepository.findAllByUser(user);
        adService.remove(ads);
    }


    public List<User> findAll(int offset, int limit, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        List<User> items = userRepository.findAll(pageRequest).getContent();
        return items;
    }

    public List<User> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));

        return userRepository.findByNameContains(name, pageRequest);
    }

    public User getUserByName(String name) {
        Optional<User> result = userRepository.findByName(name);
        return result.orElse(null);
    }

    public Integer count() {
        return Math.toIntExact(userRepository.count());
    }

    public Integer countByName(String name) {
        return Math.toIntExact(userRepository.countByNameContains(name));
    }

    public List<User> findAllByGroup(int offset, int limit, Group group, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));

        return userRepository.findAllByIdIn(getUserByGroup(group), pageRequest);
    }

    public List<User> findAllByGroupNotIn(int offset, int limit, Group group, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));

        Collection<Long> ids = getUserByGroup(group);
        if (ids.size() == 0) {
            ids = new ArrayList<>();
            ids.add(Long.valueOf(-1));
        }
        return userRepository.findAllByIdNotIn(ids, pageRequest);
    }

    public List<Long> getUserByGroup(Group group) {
        return groupUserRepository.getAllByGroupId(group.getId())
                .stream()
                .map(GroupUser::getUser)
                .collect(Collectors.toList());
    }

    public Integer countByGroupNotIn(Group group) {
        Collection<Long> ids = getUserByGroup(group);
        if (ids.size() == 0) {
            ids = new ArrayList<>();
            ids.add(Long.valueOf(-1));
        }
        return userRepository.countAllByIdNotIn(ids);
    }

    public Integer countByGroup(Group group) {
        return userRepository.countAllByIdIn(getUserByGroup(group));
    }

}
