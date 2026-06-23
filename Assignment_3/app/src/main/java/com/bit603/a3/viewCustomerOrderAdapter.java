package com.bit603.a3;

import static java.lang.String.valueOf;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bit603.a3.datamodel.MenuItem;

import java.util.List;

public class viewCustomerOrderAdapter extends ArrayAdapter<CustomerItem> {

    //private final Context context;
    //private final CustomerItem customerItem;

    public viewCustomerOrderAdapter(Context context, List<CustomerItem> items) {
        super(context, 0, items);
        //this.context = context;
        //this.customerItem = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.listview_menu_item_template, parent, false);
        }

        CustomerItem item = getItem(position);

        Log.d("ORDER_ADAPTER","position :" + position);

        TextView name = convertView.findViewById(R.id.itemName);
        TextView category = convertView.findViewById(R.id.itemCategory);
        TextView cost = convertView.findViewById(R.id.itemNumberCost);

        // item name
        // get the category name
        // get the number of things and counts of them

        name.setText(item.getItemName());
        category.setText(item.getItemCategory());
        int itemCount = item.getItemCount();
        cost.setText( itemCount + "x $" + item.getItemCost());
        return convertView;

    }

}
