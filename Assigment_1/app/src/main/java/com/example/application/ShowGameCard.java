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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.application.R;

    public class ShowGameCard extends AppCompatActivity {

        // private boolean devMode = false;
        private String card_id;
        private String name;
        private String effect_description;
        private int level;
        private int battle_power;
        private int play_cost;
        private String element;
        private String artist_name;
        private boolean unfinished;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.card_view);

            // String play_cost = getIntent().getStringExtra("play_cost"); //!!!!!
            String play_cost = String.valueOf(getIntent().getIntExtra("play_cost",0)); //!!!!!
            boolean b = play_cost instanceof String;
            Log.d("My app" , "play_cost type" + Boolean.toString(b));
            Log.d("My app" , "playcost type " + play_cost);

            String cardName = getIntent().getStringExtra("name");
            b = cardName instanceof String;
            Log.d("My app" , "cardName type" + Boolean.toString(b));
            Log.d("My app" , "cardName type " + cardName);

            //String cardName = getIntent().getStringExtra("");
            //Log.d("cardName " , cardName);

            String battle_power = String.valueOf(getIntent().getIntExtra("battle_power",0));
            //Log.d("battle_power " , battle_power);

            String level = String.valueOf(getIntent().getIntExtra("level",0));
            //Log.d("level " , level);

            String effect_description = getIntent().getStringExtra("effect_description");
            //Log.d("effect_description " , effect_description);

            String element = String.valueOf(getIntent().getStringExtra("element"));
            Log.d("My App - element " , element);

            /*
            // for the card image
            String cardName = getIntent().getStringExtra("cardName");
            Log.d("cardName " , cardName);
            */

            // set the field values
            // could probably use a loop and a couple of lookup tables.

            final TextView PlayCost = findViewById(R.id.playCost);
            PlayCost.setText("Play cost: " + play_cost);

            final TextView Name = findViewById(R.id.cardName);
            Name.setText(cardName);
            // test info
            // Name.setText("blah some stuff");

            final TextView BattlePoints = findViewById(R.id.battlePoints);
            BattlePoints.setText(battle_power + " BP");

            final TextView Effect = findViewById(R.id.effect);
            Effect.setText(effect_description);
            // Effect.setText("When summoned place a Forest Token on the battlefield that gives +20 battle power to all allies");

            final TextView Level = findViewById(R.id.level);
            Level.setText("LVL " + level);

            // use this for the character image in the next version?
            // int id = getResources().getIdentifier("yourpackagename:drawable/" + StringGenerated, null, null);

            final ImageView characterImage = findViewById(R.id.characterimage);
            characterImage.setImageResource(R.drawable.characterimage);

            final TextView Element = findViewById(R.id.element);
            Element.setText(element);
            // set the colour to the element as well
            Log.d("My App - textColour ", "setting colour");
            String textColour = GameCard.getElementColour(element);
            Log.d("My App - textColour " , textColour);
            Element.setTextColor(Color.parseColor(textColour));
            // Element.setTextColor(Color.RED);
            // Element.setTextColor(Color.parseColor("#57503C"));
            // Element.setBackgroundColor(Color.parseColor("#57503C"));

            /*
            // unused ... for now!
            final TextView CardStats = findViewById(R.id.cardStats);
            ShowTotalCards.setText(Integer.toString(totalCards));

            final TextView CardStats = findViewById(R.id.cardStats);
            ShowTotalCards.setText(Integer.toString(totalCards));
             */

        // button
        // final Button onClickCard = findViewById(R.id.showCard);
        // get the devmode pass it back when this view closes
        String devModeString = getIntent().getStringExtra(getResources().getString(R.string.intentDevMode));

        View layout = findViewById(R.id.showCard);
            layout.setOnClickListener(new View.OnClickListener() {
            // change the activity back to the list
            public void onClick(View v) {
                Intent intent = new Intent(ShowGameCard.this, RecyclerViewActivity.class);
                intent.putExtra("devModeKey", devModeString);
                startActivity(intent);
            }
        });

    }
}
