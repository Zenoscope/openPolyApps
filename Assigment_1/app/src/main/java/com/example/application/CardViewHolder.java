package com.example.application;

/**
 * Assessment 1
 *
 */

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CardViewHolder extends RecyclerView.ViewHolder {
    TextView card_id;
    TextView name;
    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        card_id = itemView.findViewById(R.id.cardNumber);
        name = itemView.findViewById(R.id.cardName);
    }
}
