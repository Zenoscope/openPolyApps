package com.bit603.a2.database.dataModel;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {Favourite.class, Recipe.class},
        version = 9
)

@TypeConverters(DataTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    //database variable
    // public static AppDatabase database = null;
    public static AppDatabase database;
    // abstract method
    // public abstract UserDao userDao();
    public abstract RecipeDao recipeDao();
    public abstract FavouriteDao favouriteDao();

    public static AppDatabase createDatabaseInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "database"
            )
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

}
