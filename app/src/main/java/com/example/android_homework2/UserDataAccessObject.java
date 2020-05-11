package com.example.android_homework2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDataAccessObject {

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM user WHERE first_name = :firstName AND last_name = :lastName")
    int deleteUser(String firstName, String lastName);
}
