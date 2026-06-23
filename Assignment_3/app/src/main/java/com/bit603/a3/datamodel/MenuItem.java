package com.bit603.a3.datamodel;

/**
 * BIT603 Assignemnt 3
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

@Entity(tableName = "menuItems")
public class MenuItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "itemID")
    public String itemID;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "category")
    public String category;

    @ColumnInfo(name = "cost")
    public int cost;

    public MenuItem(int id, String itemID, String name, String category, int cost) {
        this.id = id;
        this.itemID = itemID;
        this.name = name;
        this.category = category;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                "itemID=" + itemID +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", cost=" + cost +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getitemID() {
        return itemID;
    }

    public void setitemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
