package app.components.ads;

import app.data.ad.Ad;

import java.util.List;
import java.util.Map;

public interface AdService {
    Ad getAdById (long adID);
    void saveAd (Ad ad);
    List<Ad> findAll (int offset, int limit , Map<String, Boolean> sortOrders);
    List<Ad> findAll ();
    Integer count();
}
