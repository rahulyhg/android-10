package com.saurabh.expensetracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivityLoginBinding;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.enums.LoginEnum;
import com.saurabh.expensetracker.viewmodel.LoginViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        UserCredential credential = DaggerFactory.getAppContextComponent().getUserCredential();
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setUserCredential(credential);
        viewModel.setUserCredential(credential);
    }

    public void openSignupActivity(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void openContentScreen() {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }

    public void processLogin(View view) {
        Observer observer = new Observer<LoginEnum.LoginErrorCodes>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginEnum.LoginErrorCodes errorCodes) {
                switch (errorCodes) {
                    case EMPTY_FIELD:
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error_empty_fields), Toast.LENGTH_SHORT).show();
                        break;
                    case INCORRECT_USER_NAME_OR_PASSWORD:
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error_incorrect_user_name_or_password), Toast.LENGTH_SHORT).show();
                        break;
                    case NO_ERROR:
                        Toast.makeText(LoginActivity.this, getString(R.string.login_error_no_error), Toast.LENGTH_SHORT).show();
                        openContentScreen();
                        finish();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LoginActivity.this, getString(R.string.login_error_incorrect_user_name_or_password), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
        Observable.fromCallable(()->viewModel.processLogin()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
