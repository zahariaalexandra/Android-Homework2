package com.example.android_homework2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.style.AlignmentSpan;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecycleViewFragment extends Fragment {

    private EditText txtFirstName;
    private EditText txtLastName;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter<UserAdapter.ViewHolder> adapter;
    private List<User> users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_view, container, false);
        Button btnClearAll = view.findViewById(R.id.btnClearAll);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        Button btnDelete = view.findViewById(R.id.btnDelete);
        Button btnSync = view.findViewById(R.id.btnSync);
        AsyncTask<String, Void, List<User>> task = new SaveDataAsync().execute(null, null);

        txtFirstName = view.findViewById(R.id.txtFirstName);
        txtLastName = view.findViewById(R.id.txtLastName);
        recyclerView = view.findViewById(R.id.recyclerView);

        try {
            users = task.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);

        btnClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clearAllData();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addData();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    removeData();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        btnSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncData();
            }
        });

        return view;
    }

    private void clearAllData() throws ExecutionException, InterruptedException {
        if(users.isEmpty()) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.clear_incomplete), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            AsyncTask<List<User>, Void, Pair<Integer, List<User>>> task = new DeleteDataAsync().execute(users);
            Pair<Integer, List<User>> results = task.get();
            users = results.second;
            int deleted = results.first;

            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(users);
            recyclerView.setAdapter(adapter);

            Toast toast = Toast.makeText(getContext(), deleted + " " + getString(R.string.clear_complete), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void addData() throws ExecutionException, InterruptedException {
        if(txtLastName.getText().toString().equals("") || txtFirstName.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.input_error), Toast.LENGTH_LONG);
            toast.show();
        } else {
            User user = new User(txtFirstName.getText().toString(), txtLastName.getText().toString());
            AsyncTask<String, Void, List<User>> task = new SaveDataAsync().execute(txtFirstName.getText().toString(), txtLastName.getText().toString());

            users = task.get();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(users);
            recyclerView.setAdapter(adapter);
            txtFirstName.setText("");
            txtLastName.setText("");
        }
    }

    private void removeData() throws ExecutionException, InterruptedException {
        if(txtLastName.getText().toString().equals("") || txtFirstName.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getContext(), getString(R.string.input_error), Toast.LENGTH_LONG);
            toast.show();
        } else {
            List<User> toDelete = new ArrayList<>();
            User user = new User(txtFirstName.getText().toString(), txtLastName.getText().toString());
            toDelete.add(user);
            AsyncTask<List<User>, Void, Pair<Integer, List<User>>> task = new DeleteDataAsync().execute(toDelete);
            Pair<Integer, List<User>> results = task.get();
            int deleted = results.first;

            if(deleted != 0) {
                Toast toast = Toast.makeText(getContext(), deleted + " " + getString(R.string.delete_complete), Toast.LENGTH_LONG);
                toast.show();

                users = results.second;
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter = new UserAdapter(users);
                recyclerView.setAdapter(adapter);
                txtFirstName.setText("");
                txtLastName.setText("");
            } else {
                Toast toast = Toast.makeText(getContext(), getString(R.string.delete_incomplete), Toast.LENGTH_LONG);
                toast.show();
            }
       }
    }

    private void syncData() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Fetching data");
        progressDialog.show();

        try {
            JSONObject object = new JSONObject(loadJsonFromAssets());
            JSONArray array = object.getJSONArray("users");
            AsyncTask<String, Void, List<User>> task = null;

            for (int index = 0; index < array.length(); index++) {
                JSONObject innerObject = array.getJSONObject(index);
                String firstName = innerObject.getString("firstname");
                String lastName = innerObject.getString("lastname");
                User user = new User(firstName, lastName);

                task = new SaveDataAsync().execute(firstName, lastName);
            }

            users = task.get();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new UserAdapter(users);
            recyclerView.setAdapter(adapter);

            progressDialog.dismiss();
            Toast toast = Toast.makeText(getContext(), getString(R.string.fetch_complete), Toast.LENGTH_LONG);
            toast.show();
        } catch (JSONException e) {
            e.printStackTrace();
            progressDialog.dismiss();

            Toast toast = Toast.makeText(getContext(), getString(R.string.fetch_incomplete), Toast.LENGTH_LONG);
            toast.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String loadJsonFromAssets() {
        String json = null;

        try {
            InputStream stream = getActivity().getAssets().open("users_names.json");
            int size = stream.available();
            byte[] buffer = new byte[size];

            stream.read(buffer);
            stream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return json;
    }
}
