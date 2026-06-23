package com.bit603.a3;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewOrdersRecyclerviewHolder extends RecyclerView.ViewHolder {

    TextView orderID;

    public ViewOrdersRecyclerviewHolder(@NonNull View itemView) {
        super(itemView);
        orderID = itemView.findViewById(R.id.orderID);
        // orderCost = itemView.findViewById(R.id.orderCost);
    }
}
