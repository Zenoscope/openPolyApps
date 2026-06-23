package com.bit603.a3.datamodel;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MenuItemDao {

    @Query("SELECT * FROM menuItems")
    List<MenuItem> readAllMenuItems();

    @Query("SELECT * FROM menuItems WHERE itemID = :itemID")
    MenuItem readMenuItemByID(String itemID);

    @Insert
    void insertMenuItem(MenuItem menuItem);

    @Update
    void updateMenuItem(MenuItem menuItem);

    @Delete
    void deleteMenuItem(MenuItem menuItem);

}
