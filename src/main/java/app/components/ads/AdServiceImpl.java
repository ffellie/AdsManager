package app.components.ads;

import app.data.ad.Ad;
import app.data.ad.AdRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements  AdService{
    private AdRepository repository;
    public AdServiceImpl(AdRepository repository){
        this.repository = repository;
    }

    @Override
    public Ad getAdById (long adID){
        return repository.getById(adID);
    }

    @Override
    public void saveAd (Ad ad){
        repository.save(ad);
    }
    @Override
    public List<Ad> findAll (){
        return repository.findAll();
    }

    @Override
    public List<Ad> findAll (int offset, int limit , Map<String, Boolean> sortOrders){
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
    public List<Ad> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders){
        int page = offset / limit;
        List<Sort.Order> orders = sortOrders.entrySet().stream()
                .map(e -> new Sort.Order(e.getValue() ? Sort.Direction.ASC : Sort.Direction.DESC, e.getKey()))
                .collect(Collectors.toList());
        PageRequest pageRequest = PageRequest.of(page, limit , Sort.by(orders));
        List<Ad> items = repository.findByNameContains(name, pageRequest);
        return items;
    }
    @Override
    public Integer countByName(String name) {
        return Math.toIntExact(repository.countAllByNameContains(name));
    }

    @Override
    public Set<Ad> getAllByID(Collection<Long> adIDs) {
        return repository.findAllByIdIn(adIDs);
    }
}
