package com.shikhar.marvelpedia.Activity.Interface;

import com.shikhar.marvelpedia.Activity.ModelChars.CharsResponse;
import com.shikhar.marvelpedia.Activity.ModelComics.ComicsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelInterface {

    //base url -> "https://gateway.marvel.com:443/v1/public/"

    //get all characters, order by name, max limit is 100
    // https://gateway.marvel.com:443/v1/public/characters?orderBy=name&limit=100&ts=<timeStamp>&apikey=<key>&hash=<hash>
    @GET("characters?orderBy=name&limit=10")//max limit is 100
    Call<CharsResponse> getAllCharacters(@Query("ts") String timeStamp, @Query("apikey") String API_KEY, @Query("hash") String hash);


     //search characters, order by name, max limit is 100
    // https://gateway.marvel.com:443/v1/public/characters?nameStartsWith=<search>&orderBy=name&limit=100&ts=<timeStamp>&apikey=<key>&hash=<hash>
    @GET("characters/?orderBy=name&limit=10")//max limit is 100
    Call<CharsResponse> searchCharacters(@Query("nameStartsWith") String search, @Query("ts") String timeStamp, @Query("apikey") String API_KEY, @Query("hash") String hash);

    //get all comics, formatType =  Comic, noVariants = Trues, order by Title(Comic's Name), max Limit = 100
    // https://gateway.marvel.com:443/v1/public/characters?orderBy=name&limit=100&ts=<timeStamp>&apikey=<key>&hash=<hash>
    @GET("comics?formatType=comic&noVariants=true&orderBy=title&limit=10")//max limit is 100
    Call<ComicsResponse> getAllComics(@Query("ts") String timeStamp, @Query("apikey") String API_KEY, @Query("hash") String hash);

}
