package app.data.ad;

import app.data.ad.Ad;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AdService {
    Ad getAdById (long adID);
    void saveAd (Ad ad);
    List<Ad> findAll (int offset, int limit , Map<String, Boolean> sortOrders);
    List<Ad> findAll ();
    Integer count();
    List<Ad> findByName(int offset, int limit, String name, Map<String, Boolean> sortOrders);
    Integer countByName(String name);
    Set<Ad> getAllByID (Collection<Long> adIDs);

}
