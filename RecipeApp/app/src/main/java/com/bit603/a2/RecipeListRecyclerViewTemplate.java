package com.bit603.a2;

// -- DELETE --


/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 * Recipe list recycler view template

 * @author Ryan McArthur

 * @version 1.0

 */



import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bit603.a2.R;

public class RecipeListRecyclerViewTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.recyclerview_recipelist_itemtemplate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}