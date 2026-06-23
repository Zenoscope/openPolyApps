package com.bit603.a3.datamodel;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * App database file
 */

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {MenuItem.class, Orders.class},
        version = 2
)

@TypeConverters(DataTypeConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    //database variable
    // public static AppDatabase database = null;
    public static AppDatabase database;
    // abstract method
    public abstract MenuItemDao menuItemDao();
    public abstract OrdersDao ordersDao();

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
