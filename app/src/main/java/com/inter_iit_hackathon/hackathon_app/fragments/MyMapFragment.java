package com.inter_iit_hackathon.hackathon_app.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import com.google.maps.android.clustering.ClusterManager;
import com.inter_iit_hackathon.hackathon_app.GetPostsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.classes.ClusterPointer;
import com.inter_iit_hackathon.hackathon_app.fragments.interfaces.FragmentLifecycle;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;

public class MyMapFragment extends Fragment implements OnMapReadyCallback, FragmentLifecycle {

    private static MyMapFragment mapFragment;

    private GoogleMap mMap;
    private ClusterManager<ClusterPointer> mClusterManager;
    SessionManager sessionManager;
    LocationManager locationManager;
    RelativeLayout infoLayout;
    ImageView image;
    TextView infoText;
    TextView OKView;

    public MyMapFragment() {}

    public static MyMapFragment newInstance() {
        if (mapFragment == null)
            mapFragment = new MyMapFragment();
        return mapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        infoLayout = root.findViewById(R.id.marker_info);
        OKView = root.findViewById(R.id.oktv);
        infoText = root.findViewById(R.id.deets);
        image = root.findViewById(R.id.marker_pic);
        return root;
    }


    private void addItems(List<GetPostsQuery.GetPost> projects) {
        for (int i = 0; i < projects.size(); i++)
            mClusterManager.addItem(new ClusterPointer(
                    projects.get(i).author().name(),
                    projects.get(i).title(),
                    projects.get(i).photo(),
                    new LatLng(
                    Double.parseDouble(projects.get(i).location().lat()),
                    Double.parseDouble(projects.get(i).location().lng())
            )));
        mClusterManager.cluster();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSetup();

    }

    void mapSetup(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        } else {
            mMap.setMyLocationEnabled(true);
            mMap.clear();
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13.0f));
            mClusterManager = new ClusterManager<>(Objects.requireNonNull(this.getContext()), mMap);

            mMap.setOnCameraIdleListener(() -> mClusterManager.cluster());
            mMap.setOnMarkerClickListener(mClusterManager);
            mClusterManager.setOnClusterItemClickListener(clusterPointerListener);

            MyClient.getClient(sessionManager.getToken()).query(
                    GetPostsQuery.builder().build()
            ).enqueue(new ApolloCall.Callback<GetPostsQuery.Data>() {
                @Override
                public void onResponse(@NotNull Response<GetPostsQuery.Data> response) {
//                    getActivity().runOnUiThread(()->Toast.makeText(getActivity(), "Map", Toast.LENGTH_SHORT).show());
                    List<GetPostsQuery.GetPost> projects = response.data().getPosts();
                    getActivity().runOnUiThread(()->addItems(projects));
                }

                @Override
                public void onFailure(@NotNull ApolloException e) {

                }
            });
        }
    }

    final ClusterManager.OnClusterItemClickListener clusterPointerListener = clusterItem -> {
        JSONObject o;
        try {
            infoLayout.setVisibility(View.VISIBLE);
            o = new JSONObject(clusterItem.getSnippet());
            String urlConst = "https://res.cloudinary.com/saarang-2020/image/upload/";
            String act = o.getString("url").substring(urlConst.length()-1);
            infoText.setText("Title: "+ clusterItem.getTitle() + "\n" + "Posted By: " + o.getString("username"));
            Glide.with(image).load(urlConst +"a_-90/" +act).placeholder(R.drawable.ic_launcher_foreground).into(image);
            OKView.setOnClickListener(view -> {
                infoLayout.setVisibility(View.GONE);
                OKView.setOnClickListener(null);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    mapSetup();
                }
                return;
            }
        }
    }

    @Override
    public void onPauseFragment() {}

    @Override
    public void onResumeFragment() {}
}
