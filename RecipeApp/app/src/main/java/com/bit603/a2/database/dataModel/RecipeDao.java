package com.bit603.a2.database.dataModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import androidx.lifecycle.LiveData;

@Dao
public interface RecipeDao {

    // read all records
    @Query("SELECT * FROM recipes")
    LiveData<List<Recipe>> readAllRecipes();

    // stolen from here : https://stackoverflow.com/questions/47007860/how-to-query-a-table-with-two-where-clauses-efficiently-android-room
    // @Query("SELECT * FROM recipes WHERE " + "userID = :userID" ) // where userID is
    @Query("SELECT * FROM recipes WHERE recipeID == :recipeID")
    Recipe readSelectedRecipe(int recipeID);

    /*
    @Query("SELECT * FROM recipes WHERE userID = :userID AND recipeID = :recipeID" ) // where recipeID is
    // @Query("SELECT * FROM recipes WHERE userID = 1 AND recipeID = 1" )
    // recipe ID should be unique
    Recipe readRecipe(int userID, int recipeID);
    // List<Recipe> readRecipe(int userID, int recipeID);
    */

    // find a recipe by name
    // other options could be by prep/cook times or ingredients or rating or date

    // users name, description or ingredient
    @Query("SELECT * FROM recipes WHERE recipeTitle LIKE '%'||:recipeTitle||'%'") // where recipeID is
    List<Recipe> findRecipeByName(String recipeTitle);

    @Query("SELECT * FROM recipes WHERE recipeDescription LIKE '%'||:searchTerm||'%' OR recipeIngredients LIKE '%'||:searchTerm||'%'  ") // where recipeID is
    List<Recipe> searchRecipesNoFave(String searchTerm);

    @Query("SELECT DISTINCT recipes.* FROM recipes LEFT JOIN Favourite ON recipes.recipeID = Favourite.recipeID WHERE Favourite.userName = '%'||:searchTerm||'%' OR recipeDescription LIKE '%'||:searchTerm||'%' OR recipes.recipeIngredients LIKE '%'||:searchTerm||'%'")
    //live
    // LiveData<List<Recipe>> searchRecipesFave(String searchTerm);
    List<Recipe> searchRecipesFave(String searchTerm);

    // insert records
    @Insert
    void insertRecipe(Recipe recipe);

    //update records
    @Update
    void updateRecipe(Recipe recipe);

    // delete records
    @Delete
    void deleteRecipe(Recipe recipe);



}
