package app.routes.ads.components.list;

import app.components.search.SearchService;
import app.data.ad.AdService;
import app.data.ad.Ad;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AdSearchService implements SearchService {
    private final AdService adService;
    public List<Ad> find (int offset, int limit, String param, String filter, Map<String,Boolean> sortOrders){
        if (param!=null && !param.trim().isEmpty())
            return adService.findByName(offset,limit,param,sortOrders);
        return adService.findAll(offset,limit,sortOrders);
    }
    public int count(String name, String filter){
        if (name!=null && !name.trim().isEmpty())
            return adService.countByName(name);
        return adService.count();
    }
}
