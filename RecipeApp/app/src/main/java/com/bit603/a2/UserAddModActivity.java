package com.bit603.a2;

/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 * Add a user, and to the user database

 * @author Ryan McArthur

 * @version 1.0

 */


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserAddModActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Bit603); //re-sets the theme, otherwise it crashes

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_add_mod);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button save = findViewById(R.id.userRecipeBtn);

        // avatar buttons
        save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               clickedAddUser();
           }
        });

    }

    private void clickedAddUser() {
        // save the stuff to intent, then move to recipe list.

        TextView userNameText = findViewById(R.id.userName);
        String nameText = userNameText.getText().toString();

        Log.d("USER_MOD","username :" +  nameText);

        // pass the image and text if they are set, otherwise make some toast
        // i'm not going to check for duplicate names.
        if (nameText.isEmpty()) {
            Log.d("USER_MOD TOAST_ERR", "nameText : " + nameText);
            Toast.makeText(this, "You need to enter a name." , Toast.LENGTH_SHORT).show();
            }
        else if (nameText.length() > 20 ) {
            Toast.makeText(this, "The name you entered is more than 20 characters." , Toast.LENGTH_SHORT).show();
            }
        else {
            Intent intent = new Intent(this, RecipeListRecyclerViewActivity.class);
            intent.putExtra("userName", nameText);
            Log.d("USER_MOD TOAST_ERR", "nameText : " + nameText);
            startActivity(intent);
            }
    }

}