package com.inter_iit_hackathon.hackathon_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.classes.ClusterPointers;

import java.util.Objects;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<ClusterPointers> mClusterManager;
    public MyMapFragment() {}

    public static MyMapFragment newInstance() {
        return new MyMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;

        mapFragment.getMapAsync(this);

        return root;
    }
    private void addItems() {

        // Set some lat/lng coordinates to start with.
        double lat = 51.5145160;
        double lng = -0.1270060;

//        Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i/3;
            lat = lat + offset;
            lng = lng + offset;
            ClusterPointers offsetItem = new ClusterPointers("Heelo",new LatLng(lat,lng));
            mClusterManager.addItem(offsetItem);
        }
        mClusterManager.cluster();
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(51.5145160,-0.1270060)));
        mClusterManager = new ClusterManager<>(Objects.requireNonNull(this.getContext()),mMap);
        mMap.setOnCameraIdleListener(() -> {
                mClusterManager.cluster();
        });
        mMap.setOnMarkerClickListener(mClusterManager);
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterPointers>() {
            @Override
            public boolean onClusterItemClick(ClusterPointers clusterPointers) {
                Toast.makeText(getContext(),clusterPointers.getPosition().toString(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        addItems();
    }
}
