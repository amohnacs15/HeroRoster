package com.androidtitan.superhero.retrofit;

import com.androidtitan.superhero.model.Hero;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by amohnacs on 10/23/16.
 */

public class RetrofitService {

    private static final String BASE_URL = "https://gateway.marvel.com:443/v1/";

    public static  <S> S createService(Class<S> serviceClass) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }

    private static OkHttpClient.Builder httpClient() {
        //logger
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor);
    }


}
