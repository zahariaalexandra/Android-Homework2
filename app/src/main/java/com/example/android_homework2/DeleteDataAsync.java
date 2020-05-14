package com.example.android_homework2;

import android.os.AsyncTask;

import java.util.List;

public class DeleteDataAsync extends AsyncTask<User, Void, List<User>> {

    @Override
    protected List<User> doInBackground(User... params) {
        List<User> users;

        if(params.length == 1)
            ApplicationController.getInstance().getDatabaseInstance().userDao().deleteUser(params[0].getFirstName(), params[0].getLastName());
        else
            ApplicationController.getInstance().getDatabaseInstance().userDao().deleteAllUsers();

        users = ApplicationController.getInstance().getDatabaseInstance().userDao().getAllUsers();
        return users;
    }

    @Override
    protected void onPostExecute(List<User> users) {
        super.onPostExecute(users);
    }
}
