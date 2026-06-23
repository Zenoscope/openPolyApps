package com.bit603.a3;

/**
 * BIT603 Assignemnt 3
 *
 * Name: Ryan McArthur
 * ID: 5105426
 * Created 23/05/2026
 * Navgation hub
 */

import static java.lang.String.valueOf;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.Orders;
import com.bit603.a3.datamodel.OrdersDao;

import java.text.BreakIterator;
import java.util.List;

public class ViewOrdersRecyclerviewAdapter extends RecyclerView.Adapter<ViewOrdersRecyclerviewAdapter.ViewOrdersRecyclerviewHolder> {
    List<Orders> orders;
    Context context;

    public ViewOrdersRecyclerviewAdapter(Context context, List<Orders> orders) {
        this.context = context;
        this.orders = orders;
        }

    @NonNull
    @Override
    public ViewOrdersRecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.activity_view_orders_recyclerview_template, parent, false);
        return new ViewOrdersRecyclerviewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrdersRecyclerviewHolder holder, int position){

        // testing only
        Log.d("ORDER_ADAPTER", "orderID: " + orders.get(position).getId());
        Log.d("ORDER_ADAPTER", "order items: " + orders.get(position).getMenuItems());

        String orderID = valueOf(orders.get(position).getId());
        holder.orderID.setText(orderID);

        // click handler for the order button
        holder.itemView.setOnClickListener(v -> {
            // this will use the Order view
            Intent intent = new Intent(v.getContext(), viewCustomerOrder.class);
            intent.putExtra("orderID", orders.get(position).getId());
            intent.putExtra("orderItems", orders.get(position).getMenuItems());
            intent.putExtra("origin", "viewOrder");
            v.getContext().startActivity(intent);
        });

    }
    @Override
    public int getItemCount() { return orders.size();}

    // ✅ ViewHolder matches XML
    public static class ViewOrdersRecyclerviewHolder extends RecyclerView.ViewHolder {
        // public BreakIterator orderID;
        TextView orderID;
        //TextView orderCost;
        public ViewOrdersRecyclerviewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            //orderCost = itemView.findViewById(R.id.orderCost);
        }
    }
}
