package app.components.ads;

import app.data.ad.Ad;
import app.data.ad.AdRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdServiceImpl implements  AdService{
    private AdRepository repository;
    public AdServiceImpl(AdRepository repository){
        this.repository = repository;
    }

    public Ad getAdById (long adID){
        return repository.getById(adID);
    }

    public void saveAd (Ad ad){
        repository.save(ad);
    }
    public List<Ad> findAll (){
        return repository.findAll();
    }

    public List<Ad> findAll (int offset, int limit , Map<String, Boolean> sortOrders){
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

}