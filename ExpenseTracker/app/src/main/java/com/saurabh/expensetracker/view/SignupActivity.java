package com.saurabh.expensetracker.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.R;
import com.saurabh.expensetracker.databinding.ActivitySignupBinding;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.enums.LoginEnum;
import com.saurabh.expensetracker.viewmodel.LoginViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private Calendar calendar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySignupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        UserCredential credential = DaggerFactory.getAppContextComponent().getUserCredential();
        binding.setSignViewModel(credential);
        viewModel.setUserCredential(credential);
        calendar = Calendar.getInstance();
    }

    public void processSignUp(View view) {
        Observer observer = new Observer<LoginEnum.SignUpErrorCodes>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(LoginEnum.SignUpErrorCodes errorCode) {
                switch (errorCode) {
                    case PASSWORD_CONFIRM_PASSWORD_NOT_MATCH:
                        Toast.makeText(SignupActivity.this, getString(R.string.signup_error_password_confirm_password_mismatch), Toast.LENGTH_SHORT).show();
                        break;
                    case EMPTY_FIELD:
                        Toast.makeText(SignupActivity.this, getString(R.string.signup_error_empty_fields), Toast.LENGTH_SHORT).show();
                        break;
                    case NO_ERROR:
                        Toast.makeText(SignupActivity.this, getString(R.string.signup_error_no_error), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(SignupActivity.this, getString(R.string.signup_error_username_exists), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        };
        Observable.fromCallable(() -> viewModel.processSignUp()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }

    private DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "dd/MM/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            viewModel.setDateOfBirth(sdf.format(calendar.getTime()));
        }
    };

    public void showDatePicker(View view) {
        DatePickerDialog dialog = new DatePickerDialog(SignupActivity.this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(new Date().getTime());
        dialog.show();
    }
}
