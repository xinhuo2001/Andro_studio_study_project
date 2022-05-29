package com.example.media_lesson;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashRetrofitApi {
    @GET("photos/random")
    public Call<List<ImageModel>> randomPics(@Query("client_id") String clientId, @Query("count") int count);
}
