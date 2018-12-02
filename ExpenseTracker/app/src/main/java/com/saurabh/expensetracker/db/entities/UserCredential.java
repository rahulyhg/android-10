package com.saurabh.expensetracker.db.entities;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class UserCredential {

    @NonNull
    @PrimaryKey
    private String userName;
    private String name;
    private String gender = "Male";
    private ObservableField<String> dateOfBirth = new ObservableField<>();
    private String password;
    @Ignore
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ObservableField<String> getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth.set(dateOfBirth);
    }

    public void setDateOfBirth(ObservableField<String> dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean areAllFieldsEntered() {
        if (userName == null || name == null || password == null || confirmPassword == null)
            return false;
        if (userName.isEmpty() || dateOfBirth.get().isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
            return false;
        return true;
    }
}
