package com.saurabh.expensetracker.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivityOtpVerifierBinding;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class OtpVerifierActivity extends AppCompatActivity {
    private static final String TAG = OtpVerifierActivity.class.getSimpleName();
    private ActivityOtpVerifierBinding dataBinding;
    private int timeOutDuration = 60;
    private String verificationId = "";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp_verifier);
        dataBinding.txtEnterOtp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataBinding.btnVerifyOtp.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void sendOtp(View view) {
        String phoneNumber = dataBinding.txtMobileNo.getText().toString();
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please enter valid mobile no.", Toast.LENGTH_SHORT).show();
            return;
        }
        phoneNumber = "+91" + phoneNumber;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, timeOutDuration,
                TimeUnit.SECONDS, this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        Log.d(TAG, "Received otp code :: " + phoneAuthCredential.getSmsCode());
                        Toast.makeText(OtpVerifierActivity.this,
                                "Mobile no successfully authenticated!!",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        e.printStackTrace();
                        Toast.makeText(OtpVerifierActivity.this,
                                "Mobile no authentication failed. Please try after some time.",
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(verificationId, forceResendingToken);
                        dataBinding.layoutOtpVerification.setVisibility(View.VISIBLE);
                        OtpVerifierActivity.this.verificationId = verificationId;
                        Log.d(TAG, "Sent otp code :: " + verificationId);
                    }

                    @Override
                    public void onCodeAutoRetrievalTimeOut(String s) {
                        super.onCodeAutoRetrievalTimeOut(s);
                        dataBinding.btnResendOtp.setEnabled(true);
                    }
                });
    }

    public void verifyOtp(View view) {
        String otpEntered = dataBinding.txtEnterOtp.getText().toString();
        if (otpEntered.isEmpty()) {
            Toast.makeText(OtpVerifierActivity.this,
                    "Please enter a valid OTP.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpEntered);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(OtpVerifierActivity.this,
                            "Mobile no successfully authenticated!!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OtpVerifierActivity.this,
                            "Mobile no authentication failed. Please try after some time.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
