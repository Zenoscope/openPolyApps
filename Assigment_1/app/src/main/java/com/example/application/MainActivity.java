/**
 * Assessment 1
 *
 */

package com.example.application;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.application.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // instance variable for dev mode
    private boolean devMode;
    public static ArrayList<GameCard> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // ArrayList<GameCard> data = new ArrayList<GameCard>();
        data = GameCard.generateDataList(this);

        final TextView onClickAppVersion = findViewById(R.id.app_version);
        onClickAppVersion.setOnClickListener(new View.OnClickListener() {
            int clickCount = 0;
            public void onClick(View v) {
                // Code in this method will be executed when button is clicked
                // count the clicks and if it gets to 7, swap the background colour
                clickCount++;

                // used for testing
                //TextView testText = findViewById(R.id.app_creator);
                //testText.setText("Click count = " + clickCount);

                // check if the count is over 7 and change the mode
                // also resets the counter
                if (clickCount >= 7) {
                    clickCount = 0;
                    devMode = !devMode;
                }

                // change the background colour acccording to the devMode
                LinearLayout layout = (LinearLayout) findViewById(R.id.mainWindow);
                if (devMode)
                    layout.setBackgroundColor(
                            ContextCompat.getColor(MainActivity.this, R.color.debugBackgroundColour)
                    );
                else
                    layout.setBackgroundColor(
                            ContextCompat.getColor(MainActivity.this, R.color.screenBackgroundColour)
                    );
            }
        });

        // button for the card list
        final TextView onClickCardList = findViewById(R.id.cardList);
        onClickCardList.setOnClickListener(new View.OnClickListener() {
            // change the activity
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecyclerViewActivity.class);
                // Intent intent = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                // TODO: copies the dev mode, but as a string, not a boolean, needs fixing!
                intent.putExtra(getResources().getString(R.string.intentDevMode), String.valueOf(devMode));
                startActivity(intent);
            }
        });

        // button for the card stats
        final TextView onClickCardStats = findViewById(R.id.cardStats);
        onClickCardStats.setOnClickListener(new View.OnClickListener() {
            // change the activity
            public void onClick(View v) {
                Log.d("My App - main", String.valueOf(devMode));
                Intent intent = new Intent(MainActivity.this, CardStats.class);
                intent.putExtra(getString(R.string.intentDevMode), devMode);
                startActivity(intent);
            }
        });

        boolean devMode = getIntent().getBooleanExtra(
                getString(R.string.intentDevMode),
                false // default value
                );

    }
}