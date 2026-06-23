package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * recycler activity for viewing orders
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.Orders;
import com.bit603.a3.datamodel.OrdersDao;

import java.util.List;

public class ViewOrders extends AppCompatActivity {

        // List<User> data;
        ViewOrdersRecyclerviewAdapter adapter;

        AppDatabase db = AppDatabase.createDatabaseInstance(this);
        OrdersDao orderDao = db.ordersDao();

        List<Orders> orders = orderDao.readAllOrders();

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_view_orders);

            // recyclerview to populate
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new ViewOrdersRecyclerviewAdapter(this, orders);
            recyclerView.setAdapter(adapter);

            // buttons
            Button mainMenu = findViewById(R.id.mainMenu);
            // load the main menu
            mainMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainMenu();
                }
            });
        }

        private void mainMenu() {
            Intent intent = new Intent(this, NavigationHub.class);
            startActivity(intent);
        }

}