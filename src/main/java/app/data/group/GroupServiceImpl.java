package app.data.group;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class GroupServiceImpl implements GroupService{
    private final GroupRepository repository;

    public Group getGroupById (long groupID){
        return repository.getById(groupID);
    }

    public void saveGroup (Group group){
        repository.save(group);
    }
    public List<Group> findAll (){
        return repository.findAll();
    }

    public List<Group> findAll (int offset, int limit , Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        return repository.findAll(pageRequest).getContent();
    }

    public Integer count() {
        return Math.toIntExact(repository.count());
    }


    public List<Group> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        List<Group> items = repository.findByNameContains(name, pageRequest);
        return items;
    }
    public Integer countByName(String name) {
        return Math.toIntExact(repository.countAllByNameContains(name));
    }
}
