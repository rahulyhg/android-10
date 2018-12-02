package com.saurabh.expensetracker.viewmodel;

import android.util.Log;

import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.db.dao.UserCredentialDao;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.enums.LoginEnum;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    private static final String TAG = LoginViewModel.class.getSimpleName();
    public UserCredential userCredential;

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public LoginEnum.LoginErrorCodes processLogin() {
        Log.d(TAG, "processLogin()::User Name = " + userCredential.getUserName() + " : " + "Password = " + userCredential.getPassword());
        if (userCredential.getUserName() == null || userCredential.getUserName().isEmpty() || userCredential.getPassword() == null || userCredential.getPassword().isEmpty()) {
            return LoginEnum.LoginErrorCodes.EMPTY_FIELD;
        }
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        String password = userCredentialDao.getUserCredential(userCredential.getUserName()).getPassword();
        if (!userCredential.getPassword().equals(password)) {
            return LoginEnum.LoginErrorCodes.INCORRECT_USER_NAME_OR_PASSWORD;
        }
        return LoginEnum.LoginErrorCodes.NO_ERROR;
    }

    public LoginEnum.SignUpErrorCodes processSignUp() {
        Log.d(TAG, "processSignUp()::User Name = " + userCredential.getUserName() + " : " + "Password = " + userCredential.getPassword());
        if (!userCredential.areAllFieldsEntered()) {
            return LoginEnum.SignUpErrorCodes.EMPTY_FIELD;
        }
        String password = userCredential.getPassword();
        String confirmPassword = userCredential.getConfirmPassword();
        if (!password.equals(confirmPassword)) {
            return LoginEnum.SignUpErrorCodes.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH;
        }
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        userCredentialDao.insertUserCredential(userCredential);
        return LoginEnum.SignUpErrorCodes.NO_ERROR;
    }

    public void setDateOfBirth(String format) {
        userCredential.setDateOfBirth(format);
    }
}
