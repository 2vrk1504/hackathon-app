package com.inter_iit_hackathon.hackathon_app.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.fragments.CameraFragment;

public class CameraHolderActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_holder);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CameraFragment.newInstance())
                    .commit();
        }
//        MediaManager.init(this);
//        String requestId = MediaManager.get().upload("imageFile.jpg").dispatch();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



}
