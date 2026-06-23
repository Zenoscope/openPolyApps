package com.bit603.a3;

import static java.lang.String.valueOf;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * class for the customer items
 */

// customer order class
public class CustomerItem {

    String itemName;
    String itemCategory;
    int itemCount;
    int itemCost;

    public CustomerItem(String itemName, String itemCategory, int itemCount, int itemCost) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemCount = itemCount;
        this.itemCost = itemCost;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getItemCost() {
        return itemCost;
    }

    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    public static String itemToString(CustomerItem customerItem){
        String name = customerItem.getItemName();
        String item = customerItem.getItemCategory();
        String cost = valueOf(customerItem.getItemCost());
        String count = valueOf(customerItem.getItemCount());
        return name + " " + item + " " + cost + " " + count;
    }

}
