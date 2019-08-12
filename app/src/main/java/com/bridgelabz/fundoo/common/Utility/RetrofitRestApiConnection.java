package com.bridgelabz.fundoo.common.Utility;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRestApiConnection {

    private static String BASE_URL = "http://34.213.106.173/api/";

    private static Retrofit retrofitInstance;

    public static Retrofit openRetrofitConnection() {

        if(retrofitInstance == null) {
            retrofitInstance = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofitInstance;
    }
}
