package com.bit603.a3.datamodel;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OrdersDao {

    @Query("SELECT * FROM orders")
    List<Orders> readAllOrders();

    @Query("SELECT MAX(id) FROM orders")
    int readHighestOrder();

    @Insert
    void insertOrder(Orders order);

    @Update
    void updateRecipe(Orders order);

    @Delete
    void deleteOrder(Orders order);

}
