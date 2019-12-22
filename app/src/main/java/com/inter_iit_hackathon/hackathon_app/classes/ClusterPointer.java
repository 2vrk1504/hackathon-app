package com.inter_iit_hackathon.hackathon_app.classes;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterPointer implements ClusterItem {
    private final String title;
    private final String username;
    private final LatLng latLng;

    public ClusterPointer(String username, String title, LatLng latLng) {
        this.username = username;
        this.title = title;
        this.latLng = latLng;
    }

    @Override
    public LatLng getPosition() {  // 1
        return latLng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return username;
    }

}