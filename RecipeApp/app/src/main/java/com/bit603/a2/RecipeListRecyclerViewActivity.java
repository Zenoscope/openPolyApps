package com.bit603.a2;

/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 Recipe recycler class, activity for the recycler view

 * @author Ryan McArthur

 * @version 1.0

 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.Favourite;
import com.bit603.a2.database.dataModel.FavouriteDao;
import com.bit603.a2.database.dataModel.Recipe;
import com.bit603.a2.database.dataModel.RecipeDao;
import java.util.ArrayList;

public class RecipeListRecyclerViewActivity extends AppCompatActivity {

        RecipeListRecyclerViewAdapter adapter;
        AppDatabase db = AppDatabase.createDatabaseInstance(this);
        RecipeDao recipeDao = db.recipeDao();
        FavouriteDao favouriteDao = db.favouriteDao();

        private Button addRecipe;
        private Button searchRecipes;

        String intentUserName;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_recipe_list_recycler_view);

            Log.d("USR_RECIPE_RECORD", "on create");

            // add some default recipes, as there is no index they can be added multiple times.
            // test, add user records
            recipeDao.readAllRecipes().observe(this, recipes -> {
                        if (recipes.size() == 0) {
                            Log.d("USR_RECIPE_RECORD", "adding recipe records");
                            insertDefaultRecipeRecords();
                        }
                    });

            // list the recipes for debugging

            recipeDao.readAllRecipes().observe(this, recipes -> {
                // recipes is the actual List<Recipe> in here
                for (Recipe s : recipes) {
                    Log.d("USR_RECIPE_RECORD", s.toString());
                }
                adapter.setRecipeList(recipes);
            });

            // recyclerview to populate
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            Intent internalIntent = getIntent();
            intentUserName = internalIntent.getStringExtra("userName");
            Log.d("USR_RECIPE_RECORD", "getting user ID: " + intentUserName);

            // adapter = new RecipeListRecyclerViewAdapter(this, recipes);
            adapter = new RecipeListRecyclerViewAdapter(this, new ArrayList<>(),intentUserName);
            recyclerView.setAdapter(adapter);

            // observe LiveData and update adapter when data changes
            recipeDao.readAllRecipes().observe(this, recipes -> {
                adapter.setRecipeList(recipes);
            });

            addRecipe = findViewById(R.id.addRecipe);
            // click handler for the user button
            addRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRecipe();
                }
              });

            searchRecipes = findViewById(R.id.Search);
            // click handler for the user button
            searchRecipes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    performSearch();
                }
            });
       }

       private void insertDefaultRecipeRecords(){
            // insert default recipes
           recipeDao.insertRecipe(new Recipe("Classic Scones", 20, "A simple easy to make scone recipes that everyone will love!",  "3 cups flour\n 5 tsp baking powder\n ¼ tsp salt\n 75 g butter\n 1 cup fresh milk", "Preheat oven to 220c.\n Sift flour, baking powder, and salt into a bowl. Add butter.\n Add milk and mix. Scrape dough onto a baking sheet.\n Pat the dough into a 2 cm thick block, cut into 12 even-sized pieces.\n Bake for 10 minutes."));
           recipeDao.insertRecipe(new Recipe("Taiyaki", 20, "Taiyaki (鯛焼き) is a warm, waffle-style snack cake. ", "1¼ cups cake flour \n 1 tsp baking powder\n 1 tsp baking soda\n 3 Tbsp sugar\n 1 large egg (50 g each)\n ¾ cup whole milk (¾ cup + 4 tsp; adjust the amount of milk depending on the egg size)\n 8 Tbsp sweet red bean paste (anko) (or make my homemade Anko recipe; you can also use Nutella, custard, or your favorite filling)\n 1 Tbsp neutral oil (for greasing the grill)\n", "Sift the dry ingredients into a large bowl. Add the sugar and whisk well.\n Whisk the wet ingredients in a medium bowl.\n Combine the wet and dry ingredients.\n Refrigerate the batter for at least one hour.\n Heat the taiyaki pan over medium-low heat. Brush the mold with neutral oil. \n Pour the batter (I use a large measuring cup) until the mold is about 60% full.\n Spoon the filling into the center of the mold and pour more batter on top. Close the lid and immediately flip.\n Cook, then flip again to cook the other side. It’s done when the taiyaki is golden brown.\n" ));
           recipeDao.insertRecipe(new Recipe("Easy Muesli", 30, "A healthy muesli recipe packed with whole grains, nuts, and seeds", "3 1/2 cups rolled oats\n1/2 cup wheat bran\n1/2 teaspoon kosher salt\n1/2 teaspoon ground cinnamon\n1/2 cup sliced almonds\n1/4 cup raw pecans, coarsely chopped\n1/4 cup raw pepitas (shelled pumpkin seeds)\n1/2 cup unsweetened\ncoconut flakes\n1/4 cup dried apricots, coarsely chopped\n1/4 cup dried cherries", "Toast the grains, nuts, and seeds.\nAdd the coconut.\nTransfer to a large bowl.\nAdd the dried fruit.\nTransfer to an airtight container. \nEnjoy!"));

           // I couldn't get this to work, this isn't logged for some reason.
           /// test because of crashes
           Favourite favourite = new Favourite(1,"george");
           Log.d("USR_RECIPE_RECORD", "adding favourite:" );
           favouriteDao.insertFavourite(favourite);
       }

       private void addRecipe(){
           Intent intent = new Intent(this, AddRecipeActivity.class);
           intent.putExtra("userName", intentUserName);
           Log.d("USR_RECIPE_RECORD", "putting userName: " + intentUserName);
           startActivity(intent);
            }


        private void performSearch() {
            Intent intent = new Intent(this, RecipeSearchRecyclerViewActivity.class);
            intent.putExtra("userName", intentUserName);
            Log.d("USR_RECIPE_RECORD", "putting userName: " + intentUserName);
            startActivity(intent);
            }
}