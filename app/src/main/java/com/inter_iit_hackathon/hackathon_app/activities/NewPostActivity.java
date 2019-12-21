package com.inter_iit_hackathon.hackathon_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.textfield.TextInputLayout;
import com.inter_iit_hackathon.hackathon_app.CreatePostMutation;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

public class NewPostActivity extends AppCompatActivity {

    ImageView imageView;
    Button post;
    TextInputLayout titleTV, descTV;
    String fullPath;
    SessionManager sessionManager;
    ProgressDialog progressDialog;
    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        sessionManager = new SessionManager(this);

//        MediaManager.init(this);
        fullPath = getIntent().getStringExtra("FILENAME");

        imageView = findViewById(R.id.tempPic);
        post = findViewById(R.id.post);
        titleTV = findViewById(R.id.new_post_title);
        descTV = findViewById(R.id.new_post_description);

        File imgFile = new File(fullPath);
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
        post.setOnClickListener(view -> post());

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    void post() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1001);
        }
        else {
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            String requestId = MediaManager.get().upload(fullPath).callback(new UploadCallback() {
                @Override
                public void onStart(String requestId) {
                    progressDialog = ProgressDialog.show(NewPostActivity.this, "Please wait", "Uploading image");
                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {
                }

                @Override
                public void onSuccess(String requestId, Map resultData) {
                    progressDialog.dismiss();
                    String url = (String) resultData.get("secure_url");
                    String title = titleTV.getEditText().getText().toString();
                    String desc = descTV.getEditText().getText().toString();

                    MyClient.getClient(sessionManager.getToken()).mutate(
                            CreatePostMutation.builder()
                                .photo(url)
                                .content(desc)
                                .lat(loc.getLatitude())
                                .lng(loc.getLongitude())
                                .title(title)
                                .build()
                    ).enqueue(new ApolloCall.Callback<CreatePostMutation.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<CreatePostMutation.Data> response) {
                            runOnUiThread(()-> Toast.makeText(NewPostActivity.this, "Added New Post!", Toast.LENGTH_LONG).show());
                            finish();
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            finish();
                            runOnUiThread(()-> Toast.makeText(NewPostActivity.this, "Error occurred", Toast.LENGTH_LONG).show());
                        }
                    });
                }

                @Override
                public void onError(String requestId, ErrorInfo error) {
                    Log.e("Vallabh", "bruuuu");
                    progressDialog.dismiss();
                    Toast.makeText(NewPostActivity.this, "Error", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {
                }
            }).dispatch();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1001: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    post();
                }
                return;
            }
        }
    }
}
