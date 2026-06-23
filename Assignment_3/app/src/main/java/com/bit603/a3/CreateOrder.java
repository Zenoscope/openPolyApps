package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * recycler activity for creating orders
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.MenuItemDao;
import com.bit603.a3.datamodel.OrdersDao;

import java.util.List;

public class CreateOrder extends AppCompatActivity {

    private Button mainMenu;

    CreateOrderRecyclerviewAdapter adapter;

    AppDatabase db = AppDatabase.createDatabaseInstance(this);
    MenuItemDao menuItemDao = db.menuItemDao();
    OrdersDao ordersDao = db.ordersDao();

    List<MenuItem> menuItemList = menuItemDao.readAllMenuItems();

    Integer highestOrder = ordersDao.readHighestOrder();

    String orderString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_order);

        // recyclerview to populate
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CreateOrderRecyclerviewAdapter(this, menuItemList);
        recyclerView.setAdapter(adapter);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.videoDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Log.d("CREATE_ORDER","order string " + orderString);

        adapter.setOnOrderChangedListener((counts, items) -> {
            Integer total = 0;
            orderString = "";

            for (int i = 0; i < counts.length; i++) {

                total += counts[i] * items.get(i).getCost();

                if (counts[i] > 0) {
                    for (int j = 0; j < counts[i] ; j++) {
                        if (orderString.isEmpty()) {
                            orderString = items.get(i).getitemID();
                        } else {
                            orderString = orderString + "," + items.get(i).getitemID();
                        }
                    }
                }

                Log.d("CREATE_ORDER","order counters: " + counts[i] );
                Log.d("CREATE_ORDER","order counters: " + items.get(i).getitemID() );

            }

            Log.d("CREATE_ORDER","order total" + total);
            Log.d("CREATE_ORDER","order string " + orderString);

            // update your total TextView live
            TextView orderTotalText = findViewById(R.id.orderTotal);
            orderTotalText.setText(total.toString());

        });

        recyclerView.setAdapter(adapter);

        // buttons
        mainMenu = findViewById(R.id.mainMenu);
        // load the main menu
        mainMenu.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick (View view){
                mainMenu();
            }
        });

        // buttons
        mainMenu = findViewById(R.id.completeOrder);
        // load the main menu
        mainMenu.setOnClickListener(new View.OnClickListener()        {
            @Override
            public void onClick (View view){
                completeOrder();
            }
        });

    }

    private void mainMenu() {
        Intent intent = new Intent(this, NavigationHub.class);
        startActivity(intent);
    }

    private void completeOrder() {
        Intent intent = new Intent(this, viewCustomerOrder.class);

        // get the highest orderID from the database.
        Log.d("CREATE_ORDER", highestOrder.toString());

        intent.putExtra("orderID",highestOrder + 1);
        intent.putExtra("orderItems",orderString);
        intent.putExtra("origin","createOrder");
        startActivity(intent);
    }

}