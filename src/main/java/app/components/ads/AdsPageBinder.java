package app.components.ads;

import app.data.ad.Ad;

import java.util.Collection;

public interface AdsPageBinder {
    void onAdChangedOrCreated ();
    void onAdClicked (long adID);
    void onGroupSelected(long groupID);
    void onAddGroup();
    void onAdsSelected (Collection<Ad> selectedAds);
}
