package com.saurabh.expensetracker.db.dao;

import com.saurabh.expensetracker.db.entities.UserCredential;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserCredentialDao {
    @Insert
    void insertUserCredential(UserCredential userCredential);

    @Query("Select * from UserCredential WHERE userName = :userName")
    UserCredential getUserCredential(String userName);
}
