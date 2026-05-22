package com.bit603.a2;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.Favourite;
import com.bit603.a2.database.dataModel.FavouriteDao;
import com.bit603.a2.database.dataModel.Recipe;
import com.bit603.a2.database.dataModel.RecipeDao;

import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private AppDatabase AppDatabase;
    // private RecipeDao recipeDao;

    AppDatabase db = AppDatabase.createDatabaseInstance(this);
    RecipeDao recipeDao = db.recipeDao();
    FavouriteDao favouriteDao = db.favouriteDao();

    int intentUserID;
    int intentRecipeID;

    private Button save;
    private Button cancel;

    private boolean favourite = false;

    private String recipeTitle;
    private String recipeEstTime;
    private String recipeDescription;
    private String recipeIngredients;
    private String recipeMethod;

    Recipe recipe;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_recipe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent internalIntent = getIntent();
        // int intentUserID = internalIntent.getIntExtra("userID",-1);
        String intentUserName = internalIntent.getStringExtra("userName");
        Log.d("ADD_RECIPE", "user ID: " + intentUserName);
        int intentRecipeID = internalIntent.getIntExtra("recipeID",-1);
        Log.d("ADD_RECIPE", "recipe ID: " + intentRecipeID);

        Recipe recipe = recipeDao.readSelectedRecipe(intentRecipeID);

        Log.d("ADD_RECIPE", "favourite: " + favourite);
        // set the image to greyed out initally
        if (favourite == false ) {
            ImageView imageView = findViewById(R.id.heart);
            setImageToGrey(imageView);
        }

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView title = findViewById(R.id.recipeTitle);
                recipeTitle = title.getText().toString();

                TextView recipeTime = findViewById(R.id.recipeEstTime);
                recipeEstTime = recipeTime.getText().toString();

                TextView description = findViewById(R.id.recipeDescription);
                recipeDescription = description.getText().toString();

                TextView method = findViewById(R.id.recipeIngredients);
                recipeMethod = method.getText().toString();

                TextView ingredients = findViewById(R.id.recipeMethod);
                recipeIngredients = ingredients.getText().toString();

                boolean hasError = false;
                String error = "";

                if (recipeTitle.isEmpty()) {
                    error = "Title is required";
                    hasError = true;
                }
                if (recipeEstTime.isEmpty()) {
                    error = "The estimated time is required";
                    hasError = true;
                }
                if (recipeDescription.isEmpty()) {
                    error = "A description are required";
                    hasError = true;
                }
                if (recipeMethod.isEmpty()) {
                    error = "Instructions are required";
                    hasError = true;
                }
                if (recipeIngredients.isEmpty()) {
                    error = "Ingredients are required";
                    hasError = true;
                }

                if (hasError) {
                      Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT ).show();
                      return;  // stop here, don't save
                 }

                Log.d("ADD_RECIPE", "Save title:" + recipeTitle );
                // Integer.parseInt(recipeEstTime)
                Recipe recipe = new Recipe(recipeTitle,10,recipeDescription,recipeMethod,recipeIngredients);
                Log.d("ADD_RECIPE", "Save clicked runing rtheinxdgvindsgfkjbhdsfg" );
                clickSaveRecipe(recipe,intentUserName);
            }
        });

        cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSaveRecipe();
                Log.d("ADD_RECIPE", "Cancelled." );
            }
        });

    }

    private void clickSaveRecipe(Recipe recipe, String intentUserName){

        Log.d("ADD_RECIPE", "crashing # 0");
        AppDatabase db = AppDatabase.createDatabaseInstance(this);
        RecipeDao recipeDao = db.recipeDao();
        Log.d("ADD_RECIPE", "crashing # 1:" + recipeTitle);

        // recipeDao.insertRecipe(recipe);

        try { // insert the recipe, catch the error if there is one with the same name
            recipeDao.insertRecipe(new Recipe(
                    recipeTitle
                    , Integer.valueOf(recipeEstTime)
                    , recipeDescription
                    , recipeIngredients
                    , recipeMethod
            ));
            }
        catch (SQLiteConstraintException e) {
            Log.d("ADD_RECIPE DB_ERROR", "recipe name already exists: " + e.getMessage());
            // show a message to the user
            Toast.makeText(this, "Recipe name already exists. Please enter a different name.", Toast.LENGTH_SHORT).show();
        }

        if (favourite == true){
            List<Recipe> checkRecipes = recipeDao.findRecipeByName(recipeTitle);
            favouriteDao = AppDatabase.favouriteDao();
            // if we are adding a new recipe,m then it can't previously have been a
            // favourite, so there is no need to check if it's in the favs database already.
            Favourite favourite = new Favourite(checkRecipes.get(0).getRecipeID(),intentUserName);
            favouriteDao.insertFavourite(favourite);
            Log.d("ADD_RECIPE", "Added favourite: " + checkRecipes.toString());
        }

        Log.d("ADD_RECIPE", "Read all recipes:" + recipeDao.readAllRecipes().toString());

        // go back to the recipe list
        Intent intent = new Intent(this, RecipeListRecyclerViewActivity.class);
        intent.putExtra("userName", intentUserName);
        startActivity(intent);

    }

    //private void cancelSaveRecipe(){
    private void cancelSaveRecipe(){
        Intent intent = new Intent(this, RecipeListRecyclerViewActivity.class);
        startActivity(intent);
    }

    // public so the xml can read it
    public void heartOnClick(View view){
        // toggle the image
        setImageToGrey((ImageView) view);
        // save to the database
    }

    private void setImageToGrey(ImageView imageview){
        Log.d("ADD_RECIPE", "setImageToGrey");
        if (imageview.getColorFilter() == null) {
            imageview.setColorFilter(Color.argb(150, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
        }
        else{
            imageview.clearColorFilter();
        }
    }
}
