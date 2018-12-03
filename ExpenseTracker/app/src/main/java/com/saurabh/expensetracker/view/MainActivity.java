package com.saurabh.expensetracker.view;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.saurabh.expensetracker.R;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account == null) {
            openActivity(LoginActivity.class);
        } else {
            openActivity(ContentActivity.class);
        }
    }

    private void openActivity(Class className) {
        Intent loginIntent = new Intent(this, className);
        startActivity(loginIntent);
        finish();
    }
}
