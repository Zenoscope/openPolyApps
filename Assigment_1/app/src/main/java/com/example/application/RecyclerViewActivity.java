package com.example.application;

/**
 * Assessment 1
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    private boolean devMode;
    private String devModeString;

    List<GameCard> data;
    CardRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recycler_view);

        //generateDataList();

        // from activity_recycler_view
        RecyclerView recyclerView = findViewById(R.id.cardNameRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CardRecyclerViewAdapter(data);
        recyclerView.setAdapter(adapter);

        // back button
        final Button onClickBackButton = findViewById(R.id.backButton);
        onClickBackButton.setOnClickListener(new View.OnClickListener() {
            // change the activity
            public void onClick(View v) {
                Intent intent = new Intent(RecyclerViewActivity.this, MainActivity.class);
                // TODO this doesn't actually work
                intent.putExtra(getString(R.string.intentDevMode), devMode);
                startActivity(intent);
            }
        });

        boolean devMode = getIntent().getBooleanExtra(
                getString(R.string.intentDevMode),
                false // default value
        );

        // change the background colour according to the devMode
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.main);
        if (devMode)
            layout.setBackgroundColor(
                    ContextCompat.getColor(RecyclerViewActivity.this, R.color.debugBackgroundColour)
            );
        else
            layout.setBackgroundColor(
                    ContextCompat.getColor(RecyclerViewActivity.this, R.color.screenBackgroundColour)
            );
    }

}