package com.shikhar.marvelpedia.Activity.Interface;

import com.shikhar.marvelpedia.Activity.Model.CharsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelInterface {

    //base url -> "https://gateway.marvel.com:443/v1/public/"

    // https://gateway.marvel.com:443/v1/public/characters?orderBy=name&limit=100&ts=<timeStamp>&apikey=<key>&hash=<hash>
    @GET("characters?orderBy=name&limit=100")//max limit is 100
    Call<CharsResponse> getAllCharacters(@Query("ts") String timeStamp, @Query("apikey") String API_KEY, @Query("hash") String hash);

}
