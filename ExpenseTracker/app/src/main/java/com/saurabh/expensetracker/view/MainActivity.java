package com.saurabh.expensetracker.view;

import android.content.Intent;
import android.os.Bundle;

import com.saurabh.expensetracker.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openLoginScreen();
    }

    private void openLoginScreen() {
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
