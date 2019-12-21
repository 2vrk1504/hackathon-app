package com.inter_iit_hackathon.hackathon_app.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.GetProjectsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.activities.NewPostActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.DashboardData;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {

    public static final int OPEN_CAMERA_FOR_PICTURE = 1002;
    public static final String GET_FILENAME = "FILENAME";

    private ProgressDialog progressDialog;
    private CardStackView cardStackView;
    private FloatingActionButton fab_thumbs_up,fab_thumbs_down, fab_add_pic;
    private ArrayList<DashboardData> list = new ArrayList<>();
    private CardStackLayoutManager cardStackLayoutManager;

    private SessionManager sessionManager;

    public static DashboardFragment newInstance() {
       return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sessionManager = new SessionManager(getContext());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cardStackView = root.findViewById(R.id.cardstackview);
        fab_thumbs_down = root.findViewById(R.id.fab_down);
        fab_thumbs_up = root.findViewById(R.id.fab_up);
        fab_add_pic = root.findViewById(R.id.fab_new_pic);

        fab_add_pic.setOnClickListener(view -> startPhotoProcess());


        cardStackView.setLayoutManager(new CardStackLayoutManager(getContext()));

        cardStackLayoutManager = new CardStackLayoutManager(getContext());
        cardStackView.setLayoutManager(cardStackLayoutManager);
        final CardStackAdapter c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        MyClient.getClient(null).query(GetProjectsQuery.builder().build()).enqueue(new ApolloCall.Callback<GetProjectsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetProjectsQuery.Data> response) {
                if(response.data()!=null) {
//                    getActivity().runOnUiThread(()

                    List<GetProjectsQuery.Project> project = response.data().projects();
                    for (int i = 0; i < project.size(); i++) {
                        list.add(new DashboardData(project.get(i).photo(), project.get(i).title(), project.get(i).description(), project.get(i).updatedAt(), project.get(i).author().name()));
//                    }
//                    c.notifyDataSetChanged();
                    }
                }
                else{
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(),"Sorry, data not available",Toast.LENGTH_SHORT).show());
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                getActivity().runOnUiThread(() -> Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show());
            }
        });

        fab_thumbs_down.setOnClickListener(view -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Left).build();
            cardStackLayoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
        fab_thumbs_up.setOnClickListener(view -> {
            SwipeAnimationSetting setting = new SwipeAnimationSetting.Builder().setDirection(Direction.Right).build();
            cardStackLayoutManager.setSwipeAnimationSetting(setting);
            cardStackView.swipe();
        });
        return root;
    }

    File photoFile;

    void startPhotoProcess(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1001);
        }
        else {
            Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    Log.e("Vallabh", "bruhhhhhhh why tf", e);
                }
                if (photoFile != null) {
                    takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                    startActivityForResult(takePhotoIntent, OPEN_CAMERA_FOR_PICTURE);
                }
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == OPEN_CAMERA_FOR_PICTURE) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(getActivity(), NewPostActivity.class);
                intent.putExtra("FILENAME", photoFile.getAbsolutePath());
                startActivity(intent);
            }
        }
    }

    private File createImageFile() throws IOException {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "tempPic";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(imageFileName, ".jpg", storageDir);
            return image;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                } else {
                   startPhotoProcess();
                }
                return;
            }
        }
    }

}