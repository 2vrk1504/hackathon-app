package com.inter_iit_hackathon.hackathon_app.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.inter_iit_hackathon.hackathon_app.SignInMutation;
import com.inter_iit_hackathon.hackathon_app.SignUpMutation;

public class SessionManager {
    private static final String PREF_NAME = "HackathonApp";
    private static final String TOKEN = "token";
    private static final String NAME = "name";
    private static final String ID = "id";

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void setToken(String token){
        editor.putString(TOKEN, token);
        editor.commit();
    }
    public void setName(String name){
        editor.putString(NAME, name);
        editor.commit();
    }
    public void setId(String id){
        editor.putString(ID, id);
        editor.commit();
    }

    public String getToken(){
        return preferences.getString(TOKEN, null);
    }


    public String getName(){
        return preferences.getString(NAME, null);
    }

    public String getId(){
        return preferences.getString(ID, null);
    }

    public boolean isLoggedIn(){
        return getToken() == null;
    }

    public void setLoggedInProfile(SignInMutation.SignIn signIn){
        setToken(signIn.token());
        setId(signIn.user().id());
        setName(signIn.user().name());
    }
    public void setLoggedInProfile(SignUpMutation.SignUp signUp){
        setToken(signUp.token());
        setId(signUp.user().id());
        setName(signUp.user().name());
    }

    public void logout(){
        setToken(null);
        setName(null);
        setId(null);
    }


}
