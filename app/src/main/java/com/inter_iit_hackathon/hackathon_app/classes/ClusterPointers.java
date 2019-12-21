package com.inter_iit_hackathon.hackathon_app.classes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterPointers implements ClusterItem {
    private final String username;
    private final LatLng latLng;

    public ClusterPointers(String username, LatLng latLng) {
        this.username = username;
        this.latLng = latLng;
    }

    @Override
    public LatLng getPosition() {  // 1
        return latLng;
    }
}