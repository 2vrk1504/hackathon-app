package com.inter_iit_hackathon.hackathon_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.textfield.TextInputLayout;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.SignInMutation;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout til_username,til_password;
    TextView tv_forgot_pass;
    Button bt_login,bt_signup;

    SessionManager sess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        til_password = findViewById(R.id.tf_password);
        til_username = findViewById(R.id.tf_username);
        bt_login = findViewById(R.id.bt_login);
        bt_signup = findViewById(R.id.bt_signup);
        tv_forgot_pass = findViewById(R.id.tv_forgotpass);

        sess = new SessionManager(getApplicationContext());

        bt_login.setOnClickListener(view ->{
            String email = til_username.getEditText().getText().toString();
            String password = til_password.getEditText().getText().toString();

            // request
            MyClient.getClient().mutate(
                    SignInMutation.builder()
                    .email(email)
                    .password(password)
                    .build()
            ).enqueue(
                    new ApolloCall.Callback<SignInMutation.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<SignInMutation.Data> response) {
                            sess.setLoggedInProfile(response.data().signIn());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            LoginActivity.this.runOnUiThread(() -> Toast.makeText(LoginActivity.this,e.toString(),Toast.LENGTH_LONG).show());
                        }
                    }
            );
        });

        bt_signup.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), SignUpActivity.class);
            startActivity(intent);
            finish();
        });
        tv_forgot_pass.setOnClickListener(view -> {

        });


    }
}
