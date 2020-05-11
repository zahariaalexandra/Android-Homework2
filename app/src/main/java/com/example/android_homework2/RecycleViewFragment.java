package com.example.android_homework2;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewFragment extends Fragment {

   /* private static final String TAG = "CreateUser";
    private ToolbarListener activityCallback;*/

    private EditText txtFirstName;
    private EditText txtLastName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<User> users;
    AppDatabase appDatabase;

  /*  public interface ToolbarListener {
        public void onButtonClick(String firstName, String lastName);
    }

    public RecycleViewFragment() { }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            activityCallback = (ToolbarListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //final View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);

        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        recyclerView = view.findViewById(R.id.recyclerView);
        //final Button btnAdd = (Button) view.findViewById(R.id.btnAdd);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        appDatabase = Room.databaseBuilder(getContext(), AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();

        /*users = new ArrayList<>();

        for (int index = 0; index < 10; index++) {
            User user = new User("Ale", "Zaharia");
            users.add(user);
        }*/
        users = appDatabase.userDao().getAllUsers();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                /*User user = new User(txtFirstName.getText().toString(), txtLastName.getText().toString());
                appDatabase.userDao().insertAll(user);
                users = appDatabase.userDao().getAllUsers();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new UserAdapter(users);
                recyclerView.setAdapter(adapter);*/
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData();
            }
        });

        return view;
    }

    private void addData()
    {
        /*Toast toast = Toast.makeText(getContext(), txtFirstName.getText().toString(), Toast.LENGTH_SHORT);
        toast.show();*/
        //activityCallback.onButtonClick(txtFirstName.getText().toString(), txtLastName.getText().toString());

        if(txtLastName.getText().toString().equals("") || txtFirstName.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.input_error), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            User user = new User(txtFirstName.getText().toString(), txtLastName.getText().toString());
            appDatabase.userDao().insertAll(user);
            users = appDatabase.userDao().getAllUsers();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(users);
            recyclerView.setAdapter(adapter);
        }
    }

    private void removeData()
    {
        if(txtLastName.getText().toString().equals("") || txtFirstName.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.input_error), Toast.LENGTH_LONG);
            toast.show();
        }
        else {
            //User user = new User(txtFirstName.getText().toString(), txtLastName.getText().toString());
            int deleted = appDatabase.userDao().deleteUser(txtFirstName.getText().toString(), txtLastName.getText().toString());

            if(deleted != 0) {
                Toast toast = Toast.makeText(getContext(), deleted + " " + getString(R.string.delete_complete), Toast.LENGTH_LONG);
                toast.show();
                users = appDatabase.userDao().getAllUsers();
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new UserAdapter(users);
                recyclerView.setAdapter(adapter);
            }
            else {
                Toast toast = Toast.makeText(getContext(), getString(R.string.delete_incomplete), Toast.LENGTH_LONG);
                toast.show();
            }

       }
    }
}
