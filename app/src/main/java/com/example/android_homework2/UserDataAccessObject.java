package com.example.android_homework2;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface UserDataAccessObject {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void insertAll(User... users);
}
