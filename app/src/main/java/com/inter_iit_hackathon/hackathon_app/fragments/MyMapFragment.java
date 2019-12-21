package com.inter_iit_hackathon.hackathon_app.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.algo.GridBasedAlgorithm;
import com.inter_iit_hackathon.hackathon_app.GetPostsQuery;
import com.inter_iit_hackathon.hackathon_app.GetProjectsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.classes.ClusterPointers;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClusterManager<ClusterPointers> mClusterManager;
    SessionManager sessionManager;

    private List<ClusterPointers> clusterPointers;
    public MyMapFragment() {}

    public static MyMapFragment newInstance() {
        return new MyMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
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
        clusterPointers = new ArrayList<>();
        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterPointers>() {
            @Override
            public boolean onClusterItemClick(ClusterPointers clusterPointers) {
                Toast.makeText(getContext(),clusterPointers.getPosition().toString(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        MyClient.getClient(sessionManager.getToken()).query(
                GetProjectsQuery.builder().build()
        ).enqueue(new ApolloCall.Callback<GetProjectsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetProjectsQuery.Data> response) {
                List<GetProjectsQuery.Project> projects = response.data().projects();
                for(int i = 0; i < projects.size(); i++)
                    mClusterManager.addItem(new ClusterPointer(projects.get(i).title(), new LatLng(
                            Double.parseDouble(projects.get(i).location().lat()),
                            Double.parseDouble(projects.get(i).location().lng())
                    )));

                mClusterManager.cluster();
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {

            }
        });
    }
}
