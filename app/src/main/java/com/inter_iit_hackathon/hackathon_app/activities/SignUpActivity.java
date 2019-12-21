package com.inter_iit_hackathon.hackathon_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.inter_iit_hackathon.hackathon_app.R;
import com.inter_iit_hackathon.hackathon_app.SignInMutation;
import com.inter_iit_hackathon.hackathon_app.SignUpMutation;
import com.inter_iit_hackathon.hackathon_app.graphql_client.MyClient;
import com.inter_iit_hackathon.hackathon_app.helpers.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    TextInputLayout til_username, til_password, til_repassword;
    MaterialButton bt_signup;
    SessionManager sess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        til_username = findViewById(R.id.tf_username);
        til_password = findViewById(R.id.tf_password);
        til_repassword = findViewById(R.id.tf_re_password);
        bt_signup = findViewById(R.id.bt_signup);

        sess = new SessionManager(this);

        bt_signup.setOnClickListener(view -> {
            String user = Objects.requireNonNull(til_username.getEditText()).getText().toString();
            String pass = Objects.requireNonNull(til_password.getEditText()).getText().toString();
            String repass = Objects.requireNonNull(til_repassword.getEditText()).getText().toString();
            if (user.equals("") || pass.equals("")) {
                Snackbar.make(view.getRootView(), "Please enter details before continuing!", Snackbar.LENGTH_SHORT).show();
            } else {
                if (pass.equals(repass)) {
                    // request
                    MyClient.getClient().mutate(
                            SignUpMutation.builder()
                                    .email(user)
                                    .password(pass)
                                    .build()
                    ).enqueue(
                            new ApolloCall.Callback<SignUpMutation.Data>() {
                                @Override
                                public void onResponse(@NotNull Response<SignUpMutation.Data> response) {
                                    sess.setLoggedInProfile(response.data().signUp());
                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                    finish();
                                }
                                @Override
                                public void onFailure(@NotNull ApolloException e) {
                                    Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_LONG).show();
                                }
                            }
                    );
                } else {
                    Snackbar.make(view.getRootView(), "Make sure passwords entered match!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
