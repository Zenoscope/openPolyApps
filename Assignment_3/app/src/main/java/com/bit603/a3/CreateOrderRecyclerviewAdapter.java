package com.bit603.a3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bit603.a3.datamodel.AppDatabase;
import com.bit603.a3.datamodel.MenuItem;
import com.bit603.a3.datamodel.MenuItemDao;

import java.text.BreakIterator;
import java.util.List;

public class CreateOrderRecyclerviewAdapter extends RecyclerView.Adapter<CreateOrderRecyclerviewAdapter.CreateOrderRecyclerviewHolder> {
    List<MenuItem> menuItem;
    private AppDatabase AppDatabase;
    private MenuItemDao menuItemDao;
    Context context;
    int[] itemCounts;

    int totalCost;

    public CreateOrderRecyclerviewAdapter(Context context, List<MenuItem> menuItem) {
        this.context = context;
        this.menuItem = menuItem;
        this.itemCounts = new int[menuItem.size()];
        }

    @NonNull
    @Override
    public CreateOrderRecyclerviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View myView = inflater.inflate(R.layout.activity_create_order_recyclerview_template, parent, false);
        return new CreateOrderRecyclerviewHolder(myView);
    }
    public static class CreateOrderRecyclerviewHolder extends RecyclerView.ViewHolder {

        Button decButton;
        Button incrButton;
        TextView itemCategory;
        TextView itemName;
        TextView itemNum;
        TextView itemCost;

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

    public interface OnOrderChangedListener {
        void onOrderChanged(int[] itemCounts, List<MenuItem> menuItems);
    }

    private OnOrderChangedListener listener;

    public void setOnOrderChangedListener(OnOrderChangedListener listener) {
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(@NonNull CreateOrderRecyclerviewHolder holder, int position){

        // itemName
        holder.itemName.setText(menuItem.get(position).getName());
        holder.itemCategory.setText(menuItem.get(position).getCategory());
        holder.itemNum.setText(String.valueOf(itemCounts[position]));

        //holder.itemCost.setText(String.valueOf(menuItem.get(position).getCost()));
        // int itemCost = menuItem.get(position).getCost();
        holder.itemCost.setText(String.valueOf(menuItem.get(position).getCost()));

        // use position to track the right row's count
        holder.decButton.setOnClickListener(v -> {
            if (itemCounts[position] > 0) {
                itemCounts[position]--;
                holder.itemNum.setText(String.valueOf(itemCounts[position]));
            }

            totalCost = calculateTotal(position);

        });

        holder.incrButton.setOnClickListener(v -> {
            itemCounts[position]++;
            holder.itemNum.setText(String.valueOf(itemCounts[position]));
            calculateTotal(position);
        });
    }

    @Override
    public int getItemCount() { return menuItem.size();}

    // ✅ ViewHolder matches XML
    public void setRecipeList(List<MenuItem> newList) {
        this.menuItem = newList;
        notifyDataSetChanged();
    }

    private int calculateTotal(int position){
        if (listener != null) listener.onOrderChanged(itemCounts, menuItem);
        return itemCounts[position] * menuItem.get(position).getCost();
    }

}

