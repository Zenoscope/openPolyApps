package com.bit603.a2.database.dataModel;

/**

 * BIT603 Assessment 2

 * @version 1.0

 */


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName="recipes")

public class Recipe implements Serializable  {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name="recipeID")
    private int recipeID;

    @ColumnInfo(name="recipeTitle")
    private String recipeTitle;

    @ColumnInfo(name="estimatedTime")
    private int estimatedTime;

    @ColumnInfo(name="recipeDescription")
    private String recipeDescription;

    @ColumnInfo(name="recipeIngredients")
    private String recipeIngredients;
    // method is a formatted list.

    @ColumnInfo(name="recipeInstructions")
    private String recipeInstructions;

    public Recipe(String recipeTitle, int estimatedTime, String recipeDescription, String recipeIngredients, String recipeInstructions) {
        this.recipeTitle = recipeTitle;
        this.estimatedTime = estimatedTime;
        this.recipeDescription = recipeDescription;
        this.recipeIngredients = recipeIngredients;
        this.recipeInstructions = recipeInstructions;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipeID=" + recipeID +
                ", recipeTitle='" + recipeTitle + '\'' +
                ", estimatedTime=" + estimatedTime +
                ", recipeDescription=" + recipeDescription +
                ", recipeIngredients='" + recipeIngredients + '\'' +
                ", recipeInstructions='" + recipeInstructions + '\'' +
                '}';
    }

    public String getRecipeInstructions() {
        return recipeInstructions;
    }

    public void setRecipeInstructions(String recipeInstructions) {
        this.recipeInstructions = recipeInstructions;
    }

    public String getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String getRecipeDescription() {
        return recipeDescription;
    }

    public void setRecipeDescription(String recipeDescription) {
        this.recipeDescription = recipeDescription;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
