package app.data.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;


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
}
