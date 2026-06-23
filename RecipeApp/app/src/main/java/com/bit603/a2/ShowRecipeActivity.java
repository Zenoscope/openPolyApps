package com.bit603.a2;

/**

 * BIT603 Assessment 2

 * @version 1.0

 */


import static java.lang.String.valueOf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.Favourite;
import com.bit603.a2.database.dataModel.FavouriteDao;
import com.bit603.a2.database.dataModel.Recipe;
import com.bit603.a2.database.dataModel.RecipeDao;
import com.bit603.a2.R;

import java.util.List;

public class ShowRecipeActivity extends AppCompatActivity {

    private AppDatabase AppDatabase;
    // private RecipeDao recipeDao;

    AppDatabase db = AppDatabase.createDatabaseInstance(this);
    RecipeDao recipeDao = db.recipeDao();

    FavouriteDao favouriteDao = db.favouriteDao();
    String intentUserName;
    int intentRecipeID;

    private boolean favourite = false;

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_recipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent internalIntent = getIntent();
        intentUserName = internalIntent.getStringExtra("userName");
        Log.d("SHOW_RECIPE", "user ID 1: " + intentUserName);
        intentRecipeID = internalIntent.getIntExtra("recipeID", -1);
        Log.d("SHOW_RECIPE", "recipe ID: " + intentRecipeID);

        Recipe recipe = recipeDao.readSelectedRecipe(intentRecipeID);

        TextView titleBar = findViewById(R.id.t_titleBar);
        titleBar.setText("Your Recipe");

        TextView recipeTitle = findViewById(R.id.recipeTitle);
        recipeTitle.setText(recipe.getRecipeTitle());

        TextView recipeEstTime = findViewById(R.id.recipeEstTime);
        recipeEstTime.setText(Integer.toString(recipe.getEstimatedTime()));

        TextView recipeRating = findViewById(R.id.recipeDescription);
        recipeRating.setText(recipe.getRecipeDescription());

        TextView recipeMethod = findViewById(R.id.recipeIngredients);
        recipeMethod.setText(recipe.getRecipeIngredients());

        TextView recipeIngredients = findViewById(R.id.recipeMethod);
        recipeIngredients.setText(recipe.getRecipeInstructions());

        Button save = findViewById(R.id.userFaveBtn);
        // avatar buttons
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavouriteList();
            }
        });

        setFavouriteImage();
        showFavouriteList();
    }


    private void showFavouriteList() {
        List<Favourite> faveList = favouriteDao.getFavouriteList(intentRecipeID);

        TextView favouriteList = findViewById(R.id.FavouriteList);
        favouriteList.setVisibility(favouriteList.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);

        if (faveList.size() > 0) {

            String favListNames = "";
            for (Favourite faveName : faveList) {
                Log.d("SHOW_RECIPE", "making fav list");
                if (favListNames.isEmpty()) {
                    favListNames += faveName.getUserName();  // no comma for first item
                } else {
                    favListNames += favListNames + ", " + faveName.getUserName();
                }
                Log.d("SHOW_RECIPE", "made fav list" + favListNames);
                favouriteList.setText(favListNames);
            }
        }
    }

    public void heartOnClick(View view){
        // toggle if a fav or not
        Log.d("SHOW_RECIPE","intentUserName:"+ intentUserName);
        setImageToGrey();
        Favourite thisFavourite = new Favourite(intentRecipeID,intentUserName);
        // update the favourite list
        if (favourite == false) { //add the favourite
            favouriteDao.insertFavourite(thisFavourite);
            favourite = true;
            }
        else { // delete the favourite
            favouriteDao.deleteFavourite(thisFavourite);
            favourite = false;
        }
    }

    private void setImageToGrey(){

        ImageView favouriteImage = findViewById(R.id.heart);

        Log.d("SHOW_RECIPE", "setImageToGrey");
        if (favouriteImage.getColorFilter() == null) {
            favouriteImage.setColorFilter(Color.argb(150, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        }
        else{
            favouriteImage.clearColorFilter();
        }
    }

    private void setFavouriteImage() {
        List<Favourite> faveList = favouriteDao.getFavouriteList(intentRecipeID);

        Log.d("SHOW_RECIPE", "Image found: " + faveList.size());

        // if the image is not a fave, then set it
        if (faveList.size() == 0) {
            Log.d("ADD_RECIPE", "Image found");
            setImageToGrey();
        }
        else {
            favourite = true;
        }
    }

}

