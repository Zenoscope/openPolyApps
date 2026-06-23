/**
 * Assessment 1
 *
 */

package com.example.application;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.application.R;

public class CardList extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     EdgeToEdge.enable(this);
     setContentView(R.layout.activity_card_list);
    }
 }
