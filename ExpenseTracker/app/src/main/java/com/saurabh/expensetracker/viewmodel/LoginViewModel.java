package com.saurabh.expensetracker.viewmodel;

import android.util.Log;

import com.saurabh.expensetracker.DaggerFactory;
import com.saurabh.expensetracker.db.DatabaseProvider;
import com.saurabh.expensetracker.db.dao.UserCredentialDao;
import com.saurabh.expensetracker.db.entities.UserCredential;
import com.saurabh.expensetracker.di.component.DaggerAppContextComponent;
import com.saurabh.expensetracker.di.interfaces.ApplicationContext;
import com.saurabh.expensetracker.di.module.DatabaseProviderModule;

import javax.inject.Inject;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {
    ObservableField<UserCredential> userCredentialObservable = new ObservableField();

    public LoginViewModel() {
        userCredentialObservable.set(new UserCredential("",""));
    }

    public String getUserName() {
        return userCredentialObservable.get().getUserName();
    }

    public void setUserName(String userName) {
        userCredentialObservable.get().setUserName(userName);
    }

    public String getPassword() {
        return userCredentialObservable.get().getPassword();
    }

    public void setPassword(String password) {
        userCredentialObservable.get().setPassword(password);
    }

    public void setFullName(String fullName) {
        userCredentialObservable.get().setName(fullName);
    }

    public String getFullName() {
        return userCredentialObservable.get().getName();
    }

    public void setDateOfBirth(String dateOfBirth) {
        userCredentialObservable.get().setDateOfBirth(dateOfBirth);
    }

    public String getDateOfBirth() {
        return userCredentialObservable.get().getDateOfBirth();
    }

    public void processLogin() {
        Log.d("Awasthi","processLogin()::User Name = " + getUserName() + " : " + "Password = " + getPassword());
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        String password = userCredentialDao.getUserCredential(userCredentialObservable.get().getUserName()).getPassword();
    }

    public int processSignUp() {
        Log.d("Awasthi","processSignUp()::User Name = " + userCredentialObservable.get().getUserName() + " : " + "Password = " + userCredentialObservable.get().getPassword());
        UserCredentialDao userCredentialDao = DaggerFactory.getAppContextComponent().getDatabaseProvider().getApplicationDatabase(DaggerFactory.getAppContextComponent().getContext()).userCredentialDao();
        userCredentialDao.insertUserCredential(userCredentialObservable.get());
        return 0;
    }
}
