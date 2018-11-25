package com.saurabh.expensetracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivityLoginBinding;
import com.saurabh.expensetracker.viewmodel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        LoginViewModel viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setLoginViewModel(viewModel);
    }

    public void openSignupActivity(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }
}
