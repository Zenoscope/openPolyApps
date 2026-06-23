package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * Main Activity class, for logging in.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Youtube extends AppCompatActivity {

    YoutubeRecyclerviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_youtube);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.videoDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoutubeApiService apiService = retrofit.create(YoutubeApiService.class);

        String channelId = "UCi4gX3ydlk-53nVg386TEPg";
        String apiKey = "AIzaSyDQolFDUoeR7w71IxLlDPbYRJa92g7sybA";

        Log.d("YOUTUBE","collecting API information");

        RecyclerView recyclerView = findViewById(R.id.videoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YoutubeRecyclerviewAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(adapter);

        // get channel info
        apiService.getChannel("snippet,statistics", channelId, apiKey)
                .enqueue(new Callback<ChannelResponse>() {
                    @Override
                    public void onResponse(Call<ChannelResponse> call, Response<ChannelResponse> response) {

                        Log.d("YOUTUBE","collecting channel information");

                        if (response.isSuccessful() && response.body().items != null) {
                            ChannelResponse.Item channel = response.body().items.get(0);
                            String name = channel.snippet.title;
                            String subscribers = channel.statistics.subscriberCount;

                            Log.d("YOUTUBE","sucessful response");

                            // update UI on main thread
                            runOnUiThread(() -> {

                                String splashText = "Please check out our youtube channel " + name + ", which has " + subscribers + " subscribers." ;
                                TextView channelSplashText = findViewById(R.id.channelSplashText);
                                channelSplashText.setText(splashText);

                                //TextView subscriberCountTV = findViewById(R.id.subscriberCountTV);
                                //subscriberCountTV.setText(subscribers + " subscribers");

                                Log.d("YOUTUBE","channelNameTV" + splashText);


                            });

                            // now get videos
                            loadVideos(apiService, channelId, apiKey);
                        }
                        else if (response.isSuccessful()) {
                            Log.d("YOUTUBE","response.isSuccessful" );
                        }
                        else{
                            try {
                                Log.d("YOUTUBE", "error code: " + response.code());
                                Log.d("YOUTUBE", "error body: " + response.errorBody().string());
                            } catch (Exception e) {
                                Log.e("YOUTUBE", "error reading error body: " + e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ChannelResponse> call, Throwable t) {
                        Log.e("YOUTUBE", "channel failed: " + t.getMessage());
                    }
                });

        Button mainMenu = findViewById(R.id.mainMenu);
        // load the main menu
        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });

    }

    private void mainMenu() {
        Intent intent = new Intent(this, NavigationHub.class);
        startActivity(intent);
    }

    private void loadVideos(YoutubeApiService apiService, String channelId, String apiKey) {
        apiService.getChannelVideos("snippet", channelId, "video", "date", 20, apiKey)
                .enqueue(new Callback<YoutubeResponse>() {
                    @Override
                    public void onResponse(Call<YoutubeResponse> call, Response<YoutubeResponse> response) {
                        if (response.isSuccessful()) {
                            List<YoutubeResponse.Item> videos = response.body().items;
                            runOnUiThread(() -> {
                                adapter.setVideoList(videos);
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<YoutubeResponse> call, Throwable t) {
                        Log.e("YOUTUBE", "videos failed: " + t.getMessage());
                    }
                });
    }


}