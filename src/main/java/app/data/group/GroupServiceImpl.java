package app.data.group;

import app.data.ad.AdRepository;
import app.data.user.User;
import app.data.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository repository;
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Group getGroupById (long groupID){
        return repository.getById(groupID);
    }
    @Override
    public void saveGroup (Group group){
        repository.save(group);
    }
    @Override
    public List<Group> findAll (){
        return repository.findAll();
    }
    @Override
    public List<Group> findAll (int offset, int limit , Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        return repository.findAll(pageRequest).getContent();
    }
    @Override
    public Integer count() {
        return Math.toIntExact(repository.count());
    }

    @Override
    public List<Group> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        List<Group> items = repository.findByNameContains(name, pageRequest);
        return items;
    }
    @Override
    public Integer countByName(String name) {
        return Math.toIntExact(repository.countAllByNameContains(name));
    }
    @Override
    public void remove(Group group){
        repository.delete(group);
    }
    @Override
    public void remove(Collection<Group> groups){
        repository.deleteAll(groups);
    }

    @Override
    public void removeUserFromGroup(Group group,User user){
        List<Long> adIDs = new ArrayList<>();
        adRepository.findAllByUser(user).forEach(ad -> {adIDs.add(ad.getId());});
        promotionRepository.deleteAllByGroupAndAdIDIn(group,adIDs);
        group.getUserIDs().remove(user.getId());
        repository.save(group);
    };

    @Override
    public List<Group> findDistinctByUserIDsIn (Collection<Long> userIDs){
        return repository.findDistinctByUserIDsIn(userIDs);
    };
}
