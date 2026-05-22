package com.bit603.a2;

/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 * Recipe search recycler view activity

 * @author Ryan McArthur

 * @version 1.0

 */



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.Recipe;
import com.bit603.a2.database.dataModel.RecipeDao;

import java.util.ArrayList;
import java.util.List;

/*
recipe search - not finished yet!
 */

public class RecipeSearchRecyclerViewActivity extends AppCompatActivity {

        RecipeListRecyclerViewAdapter adapter;

        SearchView searchView;

        CheckBox checkBox;

        boolean isFavourite = true;

        private Button searchRecipes;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_recipe_search_recycler_view);

            AppDatabase db = AppDatabase.createDatabaseInstance(this);
            RecipeDao recipeDao = db.recipeDao();

            // get intent for userID for the list:
            Intent internalIntent = getIntent();
            String intentUserName = internalIntent.getStringExtra("userName");
            Log.d("USR_RECIPE_RECORD", "intentuserID " +  intentUserName);

            // recyclerview to populate
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            // create adapter with empty list first
            // adapter = new RecipeListRecyclerViewAdapter(this, new ArrayList<>());
            adapter = new RecipeListRecyclerViewAdapter(this, new ArrayList<>(),intentUserName);
            recyclerView.setAdapter(adapter);

            searchView=findViewById(R.id.searchView);
            searchView.setOnClickListener(v -> {
                searchView.setIconified(false);
                searchView.requestFocus();
            });

            checkBox=findViewById(R.id.checkbox);
            searchView.setOnClickListener(v -> {
                isFavourite = !isFavourite;
            });

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d("PERFORM_SEARCH","newText:" + query);
                    performSearch(recipeDao,query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.d("PERFORM_SEARCH","newText:" + newText);
                    performSearch(recipeDao,newText);
                    return true;
                }
            });

        }

    private void performSearch(RecipeDao recipeDao, String query) {
        Log.d("PERFORM_SEARCH","start");

        List<Recipe> results;

        if ( isFavourite == true) {
            results = recipeDao.searchRecipesFave(query);
        } else {
            results = recipeDao.searchRecipesNoFave(query);
        }

        Log.d("PERFORM_SEARCH","results" + results);
        adapter.setRecipeList(results);

        /*
        LiveData<List<Recipe>> results;

        if ( isFavourite == true) {
            results = recipeDao.searchRecipesFave(query);
        } else {
            results = recipeDao.searchRecipesNoFave(query);
        }

        Log.d("PERFORM_SEARCH","results" + results);
        adapter.setLiveRecipeList(results);
        */

    }
}
