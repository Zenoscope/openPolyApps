package com.bit603.a2.database.dataModel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavouriteDao {

    @Insert
    void insertFavourite(Favourite favourite);

    // really,when a recipe is deleted the favcourites should be updated
    // as well...
    @Delete
    void deleteFavourite(Favourite favourite);

    // Get all users with the favourite
    @Query("SELECT * FROM Favourite WHERE userName = :userName")
    List<Favourite> getUserFavourites(String userName);

    // Get all faves with the recipe ID
    @Query("SELECT * FROM Favourite WHERE recipeID = :recipeID")
    List<Favourite> getFavouriteList(int recipeID);

    //@Query("SELECT * from Favourite WHERE recipeID =:recipeID")
    //List<Favourite> getFavouriteList(int recipeID);

    // get a specific recipe from the database
    @Query("SELECT * FROM Favourite WHERE userName = :userName AND recipeID = :recipeID")
    List<Favourite> getUserRecipeFavourite(String  userName, int recipeID);

    // Get all favourites
    @Query("SELECT * FROM Favourite")
    List<Favourite> readAllFavourites();
}