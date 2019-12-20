package com.inter_iit_hackathon.hackathon_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout til_username,til_password;
    TextView tv_forgot_pass;
    Button bt_login,bt_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        til_password = findViewById(R.id.tf_password);
        til_username = findViewById(R.id.tf_username);
        bt_login = findViewById(R.id.bt_login);
        bt_signup = findViewById(R.id.bt_signup);
        tv_forgot_pass = findViewById(R.id.tv_forgotpass);

        bt_login.setOnClickListener(view -> {

        });

        bt_signup.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),SignUpActivity.class);
            startActivity(intent);
        });

        tv_forgot_pass.setOnClickListener(view -> {

        });


    }
}
