package com.inter_iit_hackathon.hackathon_app.graphql_client;

import com.apollographql.apollo.ApolloClient;
import com.inter_iit_hackathon.hackathon_app.SignInMutation;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class MyClient {

    public static MyClient instance;

    private static final String BASE_URL = "http://192.168.43.104:4000";

    ApolloClient apolloClient;
    String authToken;

    MyClient(String authToken){
        this.authToken = authToken;

        OkHttpClient okHttpClient;
        if(authToken == null){
            okHttpClient = new OkHttpClient.Builder()
                            .build();
        }
        else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(chain -> {
                        Request original = chain.request();
                        Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                        builder.header("Authorization", "Bearer "+ authToken);
                        return chain.proceed(builder.build());
                    })
                    .build();
        }
        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
    }

    public static MyClient getInstance(String authToken) {
        if(instance == null){
            instance = new MyClient(null);
        }
        else if(authToken != null && !authToken.equals(instance.authToken)){
            instance = new MyClient(authToken);
        }
        return instance;
    }

    public static ApolloClient getClient(String authToken){
        return getInstance(authToken).apolloClient;
    }

}
