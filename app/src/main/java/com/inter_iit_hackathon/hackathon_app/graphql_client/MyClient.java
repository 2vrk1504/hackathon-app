package com.inter_iit_hackathon.hackathon_app.graphql_client;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;

public class MyClient {

    private static final String BASE_URL = "<your_server>/graphql";

    OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

    ApolloClient apolloClient = ApolloClient.builder()
            .serverUrl(BASE_URL)
            .okHttpClient(okHttpClient)
            .build();

}
