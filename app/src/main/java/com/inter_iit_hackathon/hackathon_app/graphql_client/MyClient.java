package com.inter_iit_hackathon.hackathon_app.graphql_client;

import com.apollographql.apollo.ApolloClient;
import com.inter_iit_hackathon.hackathon_app.SignInMutation;

import okhttp3.OkHttpClient;

public class MyClient {

    public static MyClient instance;

    private static final String BASE_URL = "http://10.70.20.160:4000";

    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    public ApolloClient apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build();

    public static MyClient getInstance() {
        if(instance == null)
            instance = new MyClient();
        return instance;
    }

    public static ApolloClient getClient(){
        return getInstance().apolloClient;
    }
}
