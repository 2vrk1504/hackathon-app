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
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.activities.CameraHolderActivity;
import com.inter_iit_hackathon.hackathon_app.adapters.CardStackAdapter;
import com.inter_iit_hackathon.hackathon_app.classes.DashboardData;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment {

    public static final int OPEN_CAMERA_FOR_PICTURE = 1002;
    public static final String GET_FILENAME = "FILENAME";

    private ProgressDialog progressDialog;
    private CardStackView cardStackView;
    private FloatingActionButton fab_thumbs_up,fab_thumbs_down, fab_add_pic;
    private ArrayList<DashboardData> list = new ArrayList<>();

    public static DashboardFragment newInstance() {
       return new DashboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MediaManager.init(getContext());
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar Patel Road", "Very bad condition, pls halp"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar Patel Road", "Very bad condition, pls halp"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar Patel Road", "Very bad condition, pls halp"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar Patel Road", "Very bad condition, pls halp"));
        list.add(new DashboardData("https://source.unsplash.com/user/erondu/900x1600", "Sardar Patel Road", "Very bad condition, pls halp"));
        CardStackAdapter c = new CardStackAdapter(list, getContext());
        cardStackView.setAdapter(c);
        c.notifyDataSetChanged();

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
                String requestId = MediaManager.get().upload(photoFile.getPath()).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        progressDialog = ProgressDialog.show(getContext(), "Please wait", "Uploading image");
                    }
                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                    }
                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        progressDialog.dismiss();
                        String url = (String) resultData.get("secure_url");
                        Toast.makeText(getContext(), url, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.e("Vallabh", "bruuuu");
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        // your code here
                    }}).dispatch();
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