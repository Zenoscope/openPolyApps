package com.bit603.a2.database.dataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**

 * BIT603 Assessment 2

 * @version 1.0

 */



// links the favourites database to the user and recipe databses
@Entity(tableName="Favourite")

public class Favourite implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name="recipeID")
    public int recipeID;

    // add the columns

    @ColumnInfo(name="userName")
    private String userName;

    // constructor for the class
    public Favourite(int recipeID, String userName) {
        this.recipeID = recipeID;
        this.userName = userName;
    }

    // getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    // to string method
    @Override
    public String toString() {
        return "Favourite{" +
                "recipeID=" + recipeID +
                ", userName=" + userName +
                '}';
    }
}
