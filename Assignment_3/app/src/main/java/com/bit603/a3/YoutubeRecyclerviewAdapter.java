package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 31/05/2026
 * Main Activity class, for logging in.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.MenuItemDao;

import java.util.List;

public class YoutubeRecyclerviewAdapter extends RecyclerView.Adapter<YoutubeRecyclerviewAdapter.YoutubeRecyclerviewHolder> {

    List<YoutubeResponse.Item> videos;
    Context context;

    public YoutubeRecyclerviewAdapter(Context context, List<YoutubeResponse.Item> videos) {
        this.context = context;
        this.videos = videos;
    }

    @NonNull
    @Override
    public YoutubeRecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.activity_youtube_recyclerview_template, parent, false);
        return new YoutubeRecyclerviewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeRecyclerviewHolder holder, int position) {
        YoutubeResponse.Item video = videos.get(position);
        holder.videoTitle.setText(video.snippet.title);
        holder.videoDescription.setText(video.snippet.description);

        // click to play video
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Youtube.class);
            intent.putExtra("videoId", video.id.videoId);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return videos.size(); }

    public void setVideoList(List<YoutubeResponse.Item> newList) {
        this.videos = newList;
        notifyDataSetChanged();
    }

    public static class YoutubeRecyclerviewHolder extends RecyclerView.ViewHolder {
        TextView videoTitle;
        TextView videoDescription;

        public YoutubeRecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            videoTitle = itemView.findViewById(R.id.videoTitle);
            videoDescription = itemView.findViewById(R.id.videoDescription);
        }
    }
}