package com.saurabh.expensetracker.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivityOtpVerifierBinding;

import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class OtpVerifierActivity extends AppCompatActivity {
    private static final String TAG = OtpVerifierActivity.class.getSimpleName();
    private ActivityOtpVerifierBinding dataBinding;
    private int timeOutDuration = 60;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verifier);
    }

    public void sendOtp(View view) {
        String phoneNumber = dataBinding.textInputLayout.getEditText().getText().toString();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, timeOutDuration, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                Log.d(TAG, "Received otp code :: " + phoneAuthCredential.getSmsCode());
                Toast.makeText(OtpVerifierActivity.this, "Mobile no successfully authenticated!!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(OtpVerifierActivity.this, "Mobile no authentication failed. Please try after some time.",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Log.d(TAG, "Sent otp code :: " + s);
            }
        });
    }
}
