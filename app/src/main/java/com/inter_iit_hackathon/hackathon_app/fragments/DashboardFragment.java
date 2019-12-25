package com.inter_iit_hackathon.hackathon_app.fragments;

import android.Manifest;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.AddSeriousMutation;
import com.inter_iit_hackathon.hackathon_app.AddTrivialMutation;
import com.inter_iit_hackathon.hackathon_app.GetPostsQuery;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.activities.NewPostActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.Post;
import com.inter_iit_hackathon.hackathon_app.fragments.interfaces.FragmentLifecycle;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
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

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment implements FragmentLifecycle {

    public static final int OPEN_CAMERA_FOR_PICTURE = 1002;
    public static final String GET_FILENAME = "FILENAME";

    private TextView noData;
    private ProgressDialog progressDialog;
    private CardStackView cardStackView;
    private FloatingActionButton fab_thumbs_up,fab_thumbs_down;
    private ArrayList<Post> list = new ArrayList<>();
    private CardStackLayoutManager cardStackLayoutManager;
    private ProgressBar bar;
    private FloatingActionButton fab_add_pic;
    private SessionManager sessionManager;
    private CardStackAdapter c;
    private List<GetPostsQuery.GetPost> posts;
    private static int retries = 0;

    public static DashboardFragment newInstance() {
       return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sessionManager = new SessionManager(getActivity());
        posts = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        cardStackView = root.findViewById(R.id.cardstackview);
        fab_thumbs_down = root.findViewById(R.id.fab_down);
        fab_thumbs_up = root.findViewById(R.id.fab_up);
        fab_add_pic = root.findViewById(R.id.fab_new_pic);
        noData = root.findViewById(R.id.no_new_posts);

        fab_add_pic.setOnClickListener(view -> startPhotoProcess());

        cardStackLayoutManager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            int id;
            @Override
            public void onCardDragging(Direction direction, float ratio) {

            }
            @Override
            public void onCardSwiped(Direction direction) {
                if(direction == Direction.Left)
                    addTrivial(id);
                else if (direction == Direction.Right)
                    addSerious(id);
            }
            @Override
            public void onCardRewound() {}
            @Override
            public void onCardCanceled() {

            }
            @Override
            public void onCardAppeared(View view, int position) {
                id = position;
            }
            @Override
            public void onCardDisappeared(View view, int position) {

            }
        });
        cardStackView.setLayoutManager(cardStackLayoutManager);
        bar = root.findViewById(R.id.progress);
        c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        bar.setVisibility(View.VISIBLE);
//        fetchData();
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

    private void fetchData(){
        list.clear();
        MyClient.getClient(sessionManager.getToken()).query(GetPostsQuery.builder().build()).enqueue(new ApolloCall.Callback<GetPostsQuery.Data>() {
            @Override
            public void onResponse(@NotNull Response<GetPostsQuery.Data> response) {
                if(response.data()!=null) {
                    posts = response.data().getPosts();
                    if (posts!=null && posts.size() > 0) {
                        for (int i = 0; i < posts.size(); i++) {
                            if(!idInGivenList(posts.get(i).serious(), posts.get(i).trivial()))
                                list.add(new Post(
                                        posts.get(i).id(),
                                        posts.get(i).photo(),
                                        posts.get(i).title(),
                                        posts.get(i).content(),
                                        posts.get(i).updatedAt(),
                                        posts.get(i).author().name(),
                                        posts.get(i).numberOfSerious(),
                                        posts.get(i).numberOfTrivial()
                                ));
                        }
                        if(list.size() > 0)
                            showData();
                        else
                            showNoData();
                    }
                    else{
                        showNoData();
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
    }

    private boolean idInGivenList(List<GetPostsQuery.Serious> serious, List<GetPostsQuery.Trivial> trivial) {
        boolean output = false;
        String email = sessionManager.getEmail();
        for (GetPostsQuery.Serious s : serious)
            if (s.email().equals(email)) {
                output = true;
                break;
            }
        if (output) return output;
        for (GetPostsQuery.Trivial t : trivial)
            if (t.email().equals(email)) {
                output = true;
                break;
            }
        return output;
    }


    private void showData() {
        if(posts != null) {
            getActivity().runOnUiThread(()->{
                noData.setVisibility(View.GONE);
                bar.setVisibility(View.GONE);
                c.notifyDataSetChanged();
            });
        }
        else{
            fetchData();
        }
    }

    private void showNoData() {
        getActivity().runOnUiThread(() -> {
            bar.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
        });
    }

    private void addTrivial(int id) {
        Log.d("Vallabh", "adding trivial");
        MyClient.getClient(sessionManager.getToken()).mutate(
                AddTrivialMutation.builder()
                        .id(c.getItem(id).id)
                        .numberOfTrivial(c.getItem(id).dislikes)
                        .build()
        ).enqueue(
                new ApolloCall.Callback<AddTrivialMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddTrivialMutation.Data> response) {
                        Log.d("Vallabh", "added trivial");
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.d("Vallabh", "fail trivial", e);
                    }
                }
        );
    }

    private void addSerious(int id){
        Log.d("Vallabh", "adding serious");
        MyClient.getClient(sessionManager.getToken()).mutate(
                AddSeriousMutation.builder()
                        .id(c.getItem(id).id)
                        .numberOfSerious(c.getItem(id).likes)
                        .build()
        ).enqueue(
                new ApolloCall.Callback<AddSeriousMutation.Data>() {
                    @Override
                    public void onResponse(@NotNull Response<AddSeriousMutation.Data> response) {
                        Log.d("Vallabh", "added serious");
                    }

                    @Override
                    public void onFailure(@NotNull ApolloException e) {
                        Log.d("Vallabh", "fail serious", e);
                    }
                }
        );
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
        File storageDir = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        }
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

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {
        fetchData();
    }
}