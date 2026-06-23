package com.bit603.a3;

// ok!

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CreateOrderRecyclerviewHolder extends RecyclerView.ViewHolder {
    TextView itemName;
    TextView itemNum;
    TextView itemCategory;
    TextView itemCost;
    Button decButton;
    Button incrButton;

    public CreateOrderRecyclerviewHolder(@NonNull View itemView) {
        super(itemView);
        itemName = itemView.findViewById(R.id.itemName);
        itemNum = itemView.findViewById(R.id.itemNum);
        itemCategory = itemView.findViewById(R.id.itemCategory);
        decButton = itemView.findViewById(R.id.decrementItemNum);
        incrButton = itemView.findViewById(R.id.incrementItemNum);
        itemCost = itemView.findViewById(R.id.itemCost);

    }
}

