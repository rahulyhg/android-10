package com.saurabh.expensetracker.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivityLoginBinding;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.enums.LoginEnum;
import com.saurabh.expensetracker.viewmodel.LoginViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = LoginActivity.class.getSimpleName();
    private LoginViewModel viewModel;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 0;
    private UserCredential mUserCredential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mUserCredential = DaggerFactory.getAppContextComponent().getUserCredential();
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding.setUserCredential(mUserCredential);
        viewModel.setUserCredential(mUserCredential);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(v -> processGoogleSignin());
    }

    public void signInUsingOtp(View view) {
        Intent intent = new Intent(this, OtpVerifierActivity.class);
        startActivity(intent);
        finish();
    }

    public void openSignupActivity(View view) {
        openSignupActivity(false);
    }

    private void openSignupActivity(boolean isGoogleSignin) {
        Intent intent = new Intent(this, SignupActivity.class);
        intent.putExtra(LoginViewModel.INTENT_EXTRA_GOOGLE_SIGNIN, isGoogleSignin);
        startActivity(intent);
        finish();
    }

    public void openContentScreen() {
        Intent intent = new Intent(this, ContentActivity.class);
        startActivity(intent);
    }

    public void processGoogleSignin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RC_SIGN_IN:
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
                break;
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            Log.d(TAG, "Login Successful");
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            mUserCredential.setName(account.getDisplayName());
            mUserCredential.setEmail(account.getEmail());
            openSignupActivity(true);
        } catch (ApiException e) {
            Log.d(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
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
        Observable.fromCallable(() -> viewModel.processLogin()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
