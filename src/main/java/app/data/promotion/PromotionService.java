package app.data.promotion;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionRepository repository;

    public List<Promotion> getByGroup(Long group) {
        return repository.findAllByGroupId(group);
    }

    public List<Promotion> findByGroup(int offset, int limit, Long group, Map<String, Boolean> sortOrders) {
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit, Sort.by(orders));
        return repository.findAllByGroupId(group, pageRequest);
    }

    public Integer countByGroup(Long group) {
        return Math.toIntExact(repository.countAllByGroupId(group));
    }
}
