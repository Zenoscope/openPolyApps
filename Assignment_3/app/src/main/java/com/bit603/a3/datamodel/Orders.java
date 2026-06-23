package com.bit603.a3.datamodel;

/**
 * BIT603 Assignment 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * Main Activity class, for logging in.
 */

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Orders {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "menuItems")
    public String menuItems;

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", menuItems='" + menuItems + '\'' +
                '}';
    }

    public Orders(int id,String menuItems) {
        this.menuItems = menuItems;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(String menuItems) {
        this.menuItems = menuItems;
    }
}
