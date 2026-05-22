package com.bit603.a2;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.bit603.a2.database.dataModel.AppDatabase;
import com.bit603.a2.database.dataModel.RecipeDao;
import java.util.Timer;
import java.util.TimerTask;

/**

 * BIT603 Assessment 2

 * Name: Ryan McArthur

 * ID: 5105426

 * Created:  26 April 2025 (see create date)

 * Main entrry point

 * @author Ryan McArthur

 * @version 1.0

 */



/*
Main entry point:
splash
 */

public class MainActivity extends AppCompatActivity {

    AppDatabase appDatabase;
    RecipeDao recipeDao;

    int advertNumber;
    int counter = 0;

    private boolean keepSplash = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        appDatabase = AppDatabase.createDatabaseInstance(this);
        recipeDao = appDatabase.recipeDao();

        // Keep splash visible while keepSplash is true
        splashScreen.setKeepOnScreenCondition(() -> keepSplash);

        // Delay splash removal for 3 seconds
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                keepSplash = false;

                runOnUiThread(() -> {
                    Intent intent = new Intent(MainActivity.this, UserAddModActivity.class);
                    startActivity(intent);
                    finish();
                });
            }
        }, 3000);
    }

}