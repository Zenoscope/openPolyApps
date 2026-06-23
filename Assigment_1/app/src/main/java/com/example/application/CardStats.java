/**
 * Assessment 1
 *
 */

package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CardStats extends AppCompatActivity {

    private boolean devMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_card_stats);

        boolean devMode = getIntent().getBooleanExtra(
                getString(R.string.intentDevMode),
                false // default value
        );

        // a linked hashmap is ordered by how things are added.
        LinkedHashMap<String, String> statsHashMap = new LinkedHashMap<>();

        // process the card stats
        // int totalCards = getResources().getStringArray(R.array.card_idArray).length;
        //Log.d("total cards", Integer.toString(totalCards));

        // here because both use totalcards
        int totalCards = calculateTotalCards();

        // not in dev mode
        if (!devMode) {
            statsHashMap.put("Total cards", Integer.toString(totalCards));
        }

        // in dev mode
        if(devMode) {

            int totalFinished[] = calculateTotalFinished();
            statsHashMap.put("Finished cards", Integer.toString(totalFinished[0]));
            statsHashMap.put("Unfinished cards", Integer.toString(totalFinished[1]));

            int average_BP = averageBP(totalCards);
            statsHashMap.put("Average BP", Integer.toString(average_BP));

            // Display the longest card name found and the length
            String LongestName = countStringLength(getResources().getStringArray(R.array.nameArray));

            statsHashMap.put("Longest name", LongestName + " " + Integer.toString(LongestName.length()) + " chars");
            // Display the length of the longest effect string (only the number, not the effect itself)
            String LongestEffect = countStringLength(getResources().getStringArray(R.array.effectArray));
            statsHashMap.put("Longest effect text", Integer.toString(LongestEffect.length()));

            countArtistContrib(statsHashMap);
        }

        // used by both
        int highest_BP = calculateHighestBP();
        statsHashMap.put("Highest BP", Integer.toString(highest_BP));
        int lowest_BP = calculateLowestBP(highest_BP);
        statsHashMap.put("Lowest BP", Integer.toString(lowest_BP));

        // int[] cardTypes = { fire, water, earth, wind, neutral };
        int[] cardtypes = calculateCardTypes();
        statsHashMap.put("Fire cards", Integer.toString(cardtypes[0]));
        statsHashMap.put("Water cards", Integer.toString(cardtypes[1]));
        statsHashMap.put("Earth cards", Integer.toString(cardtypes[2]));
        statsHashMap.put("Wind cards", Integer.toString(cardtypes[3]));
        statsHashMap.put("Neutral cards", Integer.toString(cardtypes[4]));

        // this should really be another recycler, probably, if the number of stats gets large, but for
        // now I will be leaving it as a scrollview.

        LinearLayout container = findViewById(R.id.container);
        LayoutInflater inflater = LayoutInflater.from(this);

        for (String key : statsHashMap.keySet()) {
            View itemView = inflater.inflate(R.layout.card_stats_template, container, false);

            TextView statTitle = itemView.findViewById(R.id.statTitle);
            TextView statData = itemView.findViewById(R.id.statData);

            statTitle.setText(key);
            statData.setText(statsHashMap.get(key));

            container.addView(itemView);
        }

        // back button
        final TextView onClickCardStats = findViewById(R.id.backButton);
        onClickCardStats.setOnClickListener(new View.OnClickListener() {
            // change the activity
            public void onClick(View v) {
                Log.d("My App - main", String.valueOf(devMode));
                Intent intent = new Intent(CardStats.this, MainActivity.class);
                intent.putExtra(getString(R.string.intentDevMode), devMode);
                startActivity(intent);
            }
        });

        // change the background colour acccording to the devMode
        LinearLayout layout = (LinearLayout) findViewById(R.id.gameStats);
        if (devMode)
            layout.setBackgroundColor(
                    ContextCompat.getColor(CardStats.this, R.color.debugBackgroundColour)
            );
        else
            layout.setBackgroundColor(
                    ContextCompat.getColor(CardStats.this, R.color.screenBackgroundColour)
            );
      }

    // total cards is just a straight count of the array length
    // all of the arrays are the same size, right?
    private int calculateTotalCards() {
        return getResources().getStringArray(R.array.card_idArray).length;
    }

    private String countStringLength(String[] arrayName){
        int letterCount = 0;
        String longestName = "";
        for (String i : arrayName) {
            if ( i.length() > letterCount) {
                letterCount = i.length();
                longestName = i;
            }
        }
        return longestName;
    }

    private int[] calculateTotalFinished(){
        // total finished
        int unfinished = 0;
        int finished = 0;

        for (String i : getResources().getStringArray(R.array.unfinishedArray)) {
            if (i.equals("No")) {finished++;
                // Log.d("finished?", i); // test to see if the count is counting
            }
            else if (i.equals("Yes")) unfinished++;
            // Log.d("finished?", i);
        }

        int[] finishedCounts = {finished, unfinished};
        //Log.d("finished", Integer.toString(finished));
        // Log.d("unfinished", Integer.toString(unfinished));

        return finishedCounts;
    }

    private int calculateHighestBP() {
        // highest/lowest/average bp
        int highest_BP = 0;
        int total = 0;

        for (String i : getResources().getStringArray(R.array.battlePowerArray)) {
            int str_i = Integer.parseInt(i);
            if (str_i > highest_BP) { highest_BP = str_i;}
            // total = total + str_i;
        }
        return highest_BP;
    }

    private int calculateLowestBP(int highest_BP){
        // i don't like going through the loop again, but it's the best place to start really.
        int lowest_BP = highest_BP;
        for (String i : getResources().getStringArray(R.array.battlePowerArray)) {
            int str_i = Integer.parseInt(i);
            if (str_i < lowest_BP) { lowest_BP = str_i;}
        }
        return lowest_BP;
    }

    private int averageBP(int totalCards) {
        int total = 0;

        for (String i : getResources().getStringArray(R.array.battlePowerArray)) {
            int str_i = Integer.parseInt(i);
            total = total + str_i;
        }
        return total / totalCards;
    }


    private int[] calculateCardTypes() {
            // # fire/neutral/earth/wind/water cards
            int fire = 0;
            int earth = 0;
            int wind = 0;
            int water = 0;
            int neutral = 0;

            for (String i : getResources().getStringArray(R.array.elementArray)) {
                if (i.equals("Fire"))         fire++;
                else if (i.equals("Water"))   water++;
                else if (i.equals("Earth"))   earth++;
                else if (i.equals("Wind"))    wind++;
                else if (i.equals("Neutral")) neutral++;
            }

            int[] cardTypes = { fire, water, earth, wind, neutral };
            return cardTypes;
        }

    private void countArtistContrib(LinkedHashMap<String, String> statsHashMap) {
    // create a hashmap to store names called storedNames:
    // HashMap<String, Integer> storedNames = new HashMap<String, Integer>();

        // loop through the Artist Names
        for (String artistName : getResources().getStringArray(R.array.artistArray)) {
                // check to see if the name is in the storedNames hashmap
                if (!statsHashMap.containsKey(artistName)) {
                    // if not, add it.
                    statsHashMap.put(artistName, Integer.toString(0));
                    }
                // get the current value
                int value = Integer.parseInt(statsHashMap.get(artistName));
                //add 1 to the count for that artist,
                value++;
                // add it back, it's magic!
                statsHashMap.put(artistName, Integer.toString(value));
            }
    }

}