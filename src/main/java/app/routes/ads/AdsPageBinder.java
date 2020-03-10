package app.routes.ads;

import app.data.ad.Ad;

import java.util.Collection;

public interface AdsPageBinder {
    void onAdChangedOrCreated ();
    void onAddGroup();
}
