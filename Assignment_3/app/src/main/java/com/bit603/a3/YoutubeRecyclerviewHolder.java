package com.bit603.a3;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.MenuItemDao;

import java.util.List;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 30/05/2026
 * Main Activity class, for logging in.
 */

public class YoutubeRecyclerviewHolder extends RecyclerView.ViewHolder{

    TextView videoTitle;
    TextView videoDescription;

    public YoutubeRecyclerviewHolder(@NonNull View itemView) {
        super(itemView);

        videoTitle = itemView.findViewById(R.id.videoTitle);
        videoDescription = itemView.findViewById(R.id.videoDescription);

    }
}
