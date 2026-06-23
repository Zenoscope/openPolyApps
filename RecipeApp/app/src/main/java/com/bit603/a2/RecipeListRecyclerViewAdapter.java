package com.bit603.a2;

// -- DELETE --

/**

 * BIT603 Assessment 2

 * @version 1.0

 */


import static android.content.Intent.getIntent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.Favourite;
import com.bit603.a2.database.dataModel.FavouriteDao;
import com.bit603.a2.database.dataModel.Recipe;
import com.bit603.a2.database.dataModel.RecipeDao;
import com.bit603.a2.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecipeListRecyclerViewAdapter extends RecyclerView.Adapter<RecipeListRecyclerViewAdapter.RecipeListRecyclerViewHolder> {
    List<Recipe> recipes;

    LiveData<List<Recipe>> liveRecipes;
    private AppDatabase AppDatabase;
    private RecipeDao recipeDao;
    private FavouriteDao favouriteDao;
    Context context;

    String intentUserName;

    public RecipeListRecyclerViewAdapter(Context context, List<Recipe> recipes, String intentUserName) {
        this.context = context;
        this.recipes = recipes;
        this.intentUserName = intentUserName;
        }

    @NonNull
    @Override
public RecipeListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.recyclerview_recipelist_itemtemplate, parent, false);
        return new RecipeListRecyclerViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListRecyclerViewHolder holder, int position){

        // just needs to show the name
        Log.d("RECIPE_LIST_ADAPTER", "name: " + recipes.get(position).getRecipeTitle());

        holder.recipeName.setText(recipes.get(position).getRecipeTitle()); // name

        int recipeID  = recipes.get(position).getRecipeID();
        Log.d("RECIPE_LIST_ADAPTER", "recipe ID: " + recipeID);
        //Log.d("RECIPE_LIST_ADAPTER", "recipe vis: " + setHeartVisibility(recipeID));
        //holder.heart.setVisibility(setHeartVisibility(recipeID));

        // click handler for the user button
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ShowRecipeActivity.class);
            // get the recipe ID.
            intent.putExtra("recipeID", recipes.get(position).getRecipeID());
            intent.putExtra("userName", intentUserName);
            Log.d("RECIPE_LIST_ADAPTER","RECIPE: " + recipes.get(position).getRecipeID());
            Log.d("RECIPE_LIST_ADAPTER","userName: " + intentUserName);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() { return recipes.size();}

    // ✅ ViewHolder matches XML
    public static class RecipeListRecyclerViewHolder extends RecyclerView.ViewHolder {

        // public Activity recipeImage;
        TextView recipeName;
        ImageView heart;

        public RecipeListRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            // heart = itemView.findViewById(R.id.heart);
        }
    }


    // this just makes  it crash, i can't figure out why.
    /*
    private int setHeartVisibility(int recipeID){
        Log.d("RECIPE_LIST_ADAPTER","get faveList : " +  recipeID);
        List<Favourite> faveList = favouriteDao.getFavouriteList(recipeID);
        Log.d("RECIPE_LIST_ADAPTER","faveList : " + faveList.size());
        int visibility = View.INVISIBLE;

        if (faveList.size() > 0) {
            visibility = View.VISIBLE;
            }
        return visibility;
        }
     */

    //recipe list as List
    public void setRecipeList(List<Recipe> newList) {
        Log.d("LIST_RECYCLER","newList " + newList);
        this.recipes = newList;
        notifyDataSetChanged();
    }

    //recipe list as LiveData
    public void setLiveRecipeList(LiveData<List<Recipe>> newList) {
        Log.d("LIST_RECYCLER","newList " + newList);
        this.liveRecipes = newList;
        notifyDataSetChanged();
    }
}

