package com.bit603.a2;

/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 * Recipe recycler view holder

 * @author Ryan McArthur

 * @version 1.0

 */



import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a2.R;

// -- DELETE --

public class RecipeListRecyclerViewHolder extends RecyclerView.ViewHolder {
    ImageView recipeImage;
    TextView recipeName;

    public RecipeListRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeName = itemView.findViewById(R.id.recipeName);
        recipeImage = itemView.findViewById(R.id.heart);
    }
}
