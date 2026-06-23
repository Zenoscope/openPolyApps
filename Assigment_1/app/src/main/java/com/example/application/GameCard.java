package com.example.application;

/**
 * Assessment 1
 *
 */

import android.content.Context;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;

public class GameCard implements Serializable {
    // variables
    private String card_id;
    private String name;
    private String effect_description;
    private int level;
    private int battle_power;
    private int play_cost;
    private String element;
    // private int element;
    private String artist_name;
    private boolean unfinished;

    // constructor
    public GameCard(
            String card_id,
            String name,
            String effect_description,
            int level,
            int battle_power,
            int play_cost,
            // int element, // might need this in future, but for now:
            String element, // the elements are not actually an int in the array, it's a string.
                            // i'm not converting it to an integer just so I can look up the hex for the colour
                            // and the name, it seems foolish.
            String artist_name,
            boolean unfinished){

        setCardID(card_id);
        setName(name);
        setEffect_description(effect_description);
        setLevel(level);
        setBattlePower(battle_power);
        setPlayCost(play_cost);
        setElement(element);
        setArtistName(artist_name);
        setUnfinished(unfinished);

    }

    // getters ana setters
    public String getCardID() {
        return card_id;
    }
    public void setCardID(String card_id) {
        this.card_id = card_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String cardName) {
        this.name = cardName;
    }

    public String getEffect_description() {
        return effect_description;
    }
    public void setEffect_description(String effect_description) {
        this.effect_description = effect_description;
    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int getBattle_power() {
        return battle_power;
    }
    public void setBattlePower(int battle_power) {
        this.battle_power = battle_power;
    }

    public int getPlayCost() {
        return play_cost;
    }
    public void setPlayCost(int play_cost) {
        this.play_cost = play_cost;
    }

    /*
    // convert to string with lookup table or enum
    public int getElement() { return element;  }
    public void setElement(int element){
        this.element = element;
    }*/

    // convert to string with lookup table or enum
    public String getElement() {
        return element;
    }
    public void setElement(String element){
        this.element = element;
    }

    public String getArtist_name(){
        return artist_name;
    }
    public void setArtistName(String artist_name) {
        this.artist_name = artist_name;
    }

    public boolean getUnfinished(){
        return unfinished;
    }
    public void setUnfinished(boolean unfinished) {
        this.unfinished = unfinished;
    }

    // methods
    private static String lookupElement(String element) {
        String[][] elements = {
                {"0", "Neutral", "light grey", "#D4D4D4"},
                {"1", "Earth", "Brown", "#57503C"},
                {"2", "Wind", "Green", "#98B08D"},
                {"3", "Fire", "Red", "#691F1F"},
                {"4", "Water", "Blue", "#4D7B9E"},
                {"error", "error", "error", "#000000"}
        };

        for (int i = 0; i < elements.length; i++) {
            if (elements[i][1].equals(element)) {
                return elements[i][3];
                }
            }
        // if the element isn't found
        // should be fine, as the
        // function will expect a string, though the
        // error colour should be black or something
        Log.d("My App - GameCard", "Element not found");
        return elements[5][3];
    }

    public static String getElementColour(String element){
        return lookupElement(element);
    }

    public static ArrayList<GameCard> generateDataList(Context context){
        ArrayList<GameCard> data = new ArrayList<GameCard>();
        // loop through the card lists and create a new card for each one.
        // then add the data for each. Um. I can probably do this all at once rather than looping through each array.
        // Second method

        for (int i = 0; i < context.getResources().getStringArray(R.array.card_idArray).length; i++) {

            // get the data from the string arrays
            String card_id = context.getResources().getStringArray(R.array.card_idArray)[i];
            String name = context.getResources().getStringArray(R.array.nameArray)[i];
            String effect = context.getResources().getStringArray(R.array.effectArray)[i];
            String level = context.getResources().getStringArray(R.array.levelArray)[i];
            String battlePower = context.getResources().getStringArray(R.array.battlePowerArray)[i];
            String playCost = context.getResources().getStringArray(R.array.playCostArray)[i];
            String element = context.getResources().getStringArray(R.array.elementArray)[i];
            String artist = context.getResources().getStringArray(R.array.artistArray)[i];
            String unfinished = context.getResources().getStringArray(R.array.unfinishedArray)[i];

                /*
                String card_id,
                String name,
                String effect_description,
                int level,
                int battle_power,
                int play_cost,
                int element,
                String artist_name,
                boolean unfinished*/

            // TODO convert the element.
            // constructor

            GameCard gamecard = new GameCard(
                    card_id,
                    name,
                    effect,
                    Integer.parseInt(level),
                    Integer.parseInt(battlePower),
                    Integer.parseInt(playCost),
                    // Integer.parseInt(element),
                    element,
                    artist,
                    Boolean.valueOf(unfinished)
            );
            data.add(gamecard);
        }
        return data;
    }
}
