package com.saurabh.expensetracker.viewmodel;

import android.util.Log;

import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.db.dao.UserCredentialDao;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.enums.LoginEnum;

import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    public UserCredential userCredential;

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public void processLogin() {
        Log.d("Awasthi", "processLogin()::User Name = " + userCredential.getUserName() + " : " + "Password = " + userCredential.getPassword());
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        String password = userCredentialDao.getUserCredential(userCredential.getUserName()).getPassword();
    }

    public LoginEnum.SignUpErrorCodes processSignUp() {
        Log.d("Awasthi", "processSignUp()::User Name = " + userCredential.getUserName() + " : " + "Password = " + userCredential.getPassword());
        String password = userCredential.getPassword();
        String confirmPassword = userCredential.getConfirmPassword();
        if(!userCredential.areAllFieldsEntered()) {
            return LoginEnum.SignUpErrorCodes.EMPTY_FIELD;
        }
        if (!password.equals(confirmPassword)) {
            return LoginEnum.SignUpErrorCodes.PASSWORD_CONFIRM_PASSWORD_NOT_MATCH;
        }
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        userCredentialDao.insertUserCredential(userCredential);
        return LoginEnum.SignUpErrorCodes.NO_ERROR;
    }

    public void setDateOfBirth(String format) {
        userCredential.getDateOfBirth().set(format);
    }
}
