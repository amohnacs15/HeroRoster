package com.androidtitan.superhero.retrofit;

import com.androidtitan.superhero.model.Hero;
import com.androidtitan.superhero.model.Heroes;
import com.androidtitan.superhero.model.MarvelResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by amohnacs on 10/23/16.
 */

public interface RetrofitEndpoint {

    @GET("public/characters")
    Call<MarvelResponse> requestHeroList(@Query("orderBy") String orderBy, @Query("limit") int limit,
                                         @Query("ts") long timeStamp, @Query("apikey") String key,
                                         @Query("hash") String hash
                                    );

    @GET("public/characters/{characterId}")
    Call<MarvelResponse> requestHero(@Path("characterId") int characterId,
                                         @Query("ts") long timeStamp, @Query("apikey") String key,
                                         @Query("hash") String hash
    );

}
