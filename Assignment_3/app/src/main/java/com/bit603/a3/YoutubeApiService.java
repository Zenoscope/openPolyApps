package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 30/05/2026
 * youtube API class
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YoutubeApiService {

    // get channel info (name, subscriber count)
    @GET("channels")
    Call<ChannelResponse> getChannel(
            @Query("part") String part,
            @Query("id") String channelId,
            @Query("key") String apiKey
    );

    // get videos from a channel
    @GET("search")
    Call<YoutubeResponse> getChannelVideos(
            @Query("part") String part,
            @Query("channelId") String channelId,
            @Query("type") String type,
            @Query("order") String order,
            @Query("maxResults") int maxResults,
            @Query("key") String apiKey
    );
}
