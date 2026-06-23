package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * Navgation hub
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.Orders;
import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.MenuItemDao;
import com.bit603.a3.datamodel.OrdersDao;


public class NavigationHub extends AppCompatActivity {

    AppDatabase appDatabase;
    MenuItem menuItemDao;
    Orders ordersDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_button_hub);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.videoDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button orderButton = (Button)findViewById(R.id.orderButton);
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder();
            }
        });

        Button viewHistory = (Button)findViewById(R.id.viewHistory);
        viewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewOrders();
            }
        });

        Button viewYoutube = (Button)findViewById(R.id.viewYoutube);
        viewYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                youtubeActivity();
            }
        });

        appDatabase = AppDatabase.createDatabaseInstance(this);
        MenuItemDao menuItemDao = appDatabase.menuItemDao();

        if(menuItemDao.readAllMenuItems().size() == 0) {
            Log.d( "USR_RECORD", "adding user records");
            insertItemRecords(menuItemDao);
        }

    }

    private void createOrder(){
        Intent intent = new Intent(this, CreateOrder.class);
        startActivity(intent);
    }

    private void viewOrders(){
        Intent intent = new Intent(this, ViewOrders.class);
        startActivity(intent);
    }

    private void youtubeActivity(){
        Intent intent = new Intent(this, Youtube.class);
        startActivity(intent);
    }

    private void insertItemRecords(MenuItemDao menuItemDao){
        //try {
        // public MenuItem(int id, String name, String category, int cost)
        // main\

        menuItemDao.insertMenuItem(new MenuItem(1, "001", "Pork cutlet curry","main", 10));
        menuItemDao.insertMenuItem(new MenuItem(2, "002","Vegetable curry","main", 9));
        menuItemDao.insertMenuItem(new MenuItem(3, "003","Cheese curry","main", 10));

        // side
        menuItemDao.insertMenuItem(new MenuItem(4, "004","Green salad","side", 5));
        menuItemDao.insertMenuItem(new MenuItem(5, "005","Icecream","side", 7));
        menuItemDao.insertMenuItem(new MenuItem(6, "006","Potato salad","side", 5));

        // drink
        menuItemDao.insertMenuItem(new MenuItem(7, "007","Coca-cola","drink", 3));
        menuItemDao.insertMenuItem(new MenuItem(8, "008","Lassi","drink", 3));
        menuItemDao.insertMenuItem(new MenuItem(9, "009","Iced coffee","drink", 3));

        // Order
        OrdersDao ordersDao = appDatabase.ordersDao();
        ordersDao.insertOrder(new Orders(1, "008,007,006"));
        ordersDao.insertOrder(new Orders(2, "008,007,006"));
        ordersDao.insertOrder(new Orders(3, "008,008,007,006,006,002,006"));
    }

}