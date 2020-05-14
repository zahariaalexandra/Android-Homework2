package com.example.android_homework2;

import android.os.AsyncTask;
import android.util.Pair;

import java.util.List;

public class DeleteDataAsync extends AsyncTask<List<User>, Void, Pair<Integer, List<User>>> {

    @Override
    protected Pair<Integer, List<User>> doInBackground(List<User>... params) {
        List<User> users;
        int rowsDeleted;

        if(params[0].size() == 1)
            rowsDeleted = ApplicationController.getInstance().getDatabaseInstance().userDao().deleteUser(params[0].get(0).getFirstName(), params[0].get(0).getLastName());
        else
            rowsDeleted = ApplicationController.getInstance().getDatabaseInstance().userDao().deleteAllUsers();

        users = ApplicationController.getInstance().getDatabaseInstance().userDao().getAllUsers();
        return new Pair<>(rowsDeleted, users);
    }

    @Override
    protected void onPostExecute(Pair<Integer, List<User>> results) {
        super.onPostExecute(results);
    }
}
