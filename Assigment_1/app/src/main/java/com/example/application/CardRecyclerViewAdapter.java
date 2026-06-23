package com.example.application;

/**
 * Assessment 1
 *
 */

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardViewHolder> {
    // this is the card
    ArrayList<GameCard> data;
    public CardRecyclerViewAdapter(List<GameCard> data) {
            this.data = MainActivity.data;
            }
    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // hiding cards goes here?

        View myView = inflater.inflate(R.layout.layout_recycler_view_list_item, parent, false);
        return new CardViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        // these are the get/setters from the functions
        // this is used by the CardViewHolder.java file to set the information
        // in the list of cards
        holder.card_id.setText(data.get(position).getCardID());
        holder.name.setText(data.get(position).getName());
        // TODO add the element to the card list

        // click handler for the card list
        holder.itemView.setOnClickListener(v -> {

            // TODO fix the rest of this. Probably not the best way to do this,
            // i should be passing the whole card really.
            Intent intent = new Intent(v.getContext(), ShowGameCard.class);
            // get the card ID.
            intent.putExtra("card_id", data.get(position).getCardID());
            intent.putExtra("name", data.get(position).getName());
            // check this is actually getting parsed
            // Log.d("CRVA - cardName " , data.get(position).getName());
            // could use consts for the strings instead....
            // public static final String EXTRA_LEVEL = "level";
            intent.putExtra("effect_description", data.get(position).getEffect_description());
            intent.putExtra("level", data.get(position).getLevel());
            intent.putExtra("battle_power", data.get(position).getBattle_power());
            intent.putExtra("play_cost", data.get(position).getPlayCost());
            intent.putExtra("element", data.get(position).getElement());
            Log.d("My App - CRVA " , data.get(position).getElement());
            intent.putExtra("artist_name", data.get(position).getArtist_name());
            intent.putExtra("unfinished", data.get(position).getUnfinished());

            v.getContext().startActivity(intent);
        });
    }
    @Override
    public int getItemCount() { return data.size();}
}



