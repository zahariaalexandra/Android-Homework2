package com.example.android_homework2;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDataAccessObject userDao();
}
