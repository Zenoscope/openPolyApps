package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * View the order
 */

import static java.lang.String.valueOf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.MenuItemDao;
import com.bit603.a3.datamodel.Orders;
import com.bit603.a3.datamodel.OrdersDao;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewCustomerOrder extends AppCompatActivity {

    AppDatabase appDatabase;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_customer_order);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.videoDescription), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listView = findViewById(R.id.myListView);  // get the listview
        // access the menuItems database so we can get info about each item.
        appDatabase = AppDatabase.createDatabaseInstance(this);
        MenuItemDao menuItemDao = appDatabase.menuItemDao();

        Intent internalIntent = getIntent();
        // sets the order ID on the Customer Order view
        int orderID = internalIntent.getIntExtra("orderID",-1);
        // get the list of menu items
        String orderItems = internalIntent.getStringExtra("orderItems");
        // get where it came from to set up the bottom buttons.
        String origin = internalIntent.getStringExtra("origin");

        Map<String, Integer> itemCount = new HashMap<>();
        // split the order at the commas.
        String[] arr_split = orderItems.split(",");

        // count each item
        for (String item : arr_split) {
            if (itemCount.containsKey(item)) {
                itemCount.put(item, itemCount.get(item) + 1);  // increment count
            } else {
                itemCount.put(item, 1);  // first time seeing it
            }
        }

        List<CustomerItem> customerItemList = new ArrayList<>();

        int calculatedTotal = 0;

        // print results
        for (Map.Entry<String, Integer> entry : itemCount.entrySet()) {
            int numItems = entry.getValue();
            Log.d( "VIEW_ORDER", "Item #: " + entry.getKey() + " Count: " + numItems );

            MenuItem menuItem = menuItemDao.readMenuItemByID( entry.getKey() );

            String itemName = menuItem.getName();
            String itemCategory = menuItem.getCategory();
            int itemCost = menuItem.getCost();
            calculatedTotal = calculatedTotal + (itemCost * numItems);
            CustomerItem customerItem = new CustomerItem(itemName, itemCategory,numItems, itemCost);
            customerItemList.add(customerItem);
        }

        for (CustomerItem item : customerItemList) {
            Log.d("VIEW_ORDER", "customer items: " +  CustomerItem.itemToString(item));
        }
        Log.d("VIEW_ORDER", "total cost: "  +  calculatedTotal );


        viewCustomerOrderAdapter adapter = new viewCustomerOrderAdapter(this, customerItemList);
        listView.setAdapter(adapter);

        TextView orderTotal = findViewById(R.id.orderTotal);
        orderTotal.setText("Total: $" + valueOf(calculatedTotal));

        Button customerButton = findViewById(R.id.customerButton);

        if (origin.equals("viewOrder")) {
            // button magic stuff
            // if the origin is this one, then just show a back button, which goes back to the order list.
            // set the text of the button

            Log.d("VIEW_CUSTOMER", "you clicked the view order button!");

            customerButton.setText("Back");
            customerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    originViewOrder();
                }
            });
        }
        else if (origin.equals("createOrder")){
            // if this is the origin, then the button places the order.
            // order button is vsible, back button as well

            Log.d("VIEW_CUSTOMER", "you clicked the create order button!");
            customerButton.setText("Create order");

            customerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    originCreateOrder(orderID, orderItems);
                }
            });
        }
    }

    private void originViewOrder(){
        // show the button as "back"

        Intent intent = new Intent( this , ViewOrders.class);
        startActivity(intent);
    }

    private void originCreateOrder(int orderID, String orderItems){

        // write to the database
        OrdersDao ordersDao = appDatabase.ordersDao();
        Orders customerOrder = new Orders(orderID, orderItems);
        ordersDao.insertOrder(customerOrder);

        // upload to firebase

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("orders")
                .add(customerOrder)
                .addOnSuccessListener(documentReference -> {
                    Log.d("SAVE_ORDER", "saved to Firestore: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Log.e("SAVE_ORDER", "failed to save to Firestore: " + e.getMessage());
                });

        // return to the main screen
        Intent intent = new Intent(this , CreateOrder.class);
        startActivity(intent);

    }

}