package com.example.android_homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity{//FragmentActivity implements RecycleViewFragment.ToolbarListener {

    /*EditText txtFirstName;
    EditText txtLastName;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, new RecycleViewFragment());
        transaction.commit();

       /* FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        RecycleViewFragment fragment = new RecycleViewFragment();
        transaction.add(R.id.recycleViewFragment, fragment);
        transaction.commit();*/
/*
        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);*/
    }

    /*public void onButtonClick(String firstName, String lastName) {
        Toast toast = Toast.makeText(getApplicationContext(), "First name: " + firstName, Toast.LENGTH_SHORT);
        toast.show();
    }*/

    /*public void addPerson(View view)
    {
        String firstName = txtFirstName.getText().toString();
        Toast toast = Toast.makeText(getApplicationContext(), "aa" + firstName, Toast.LENGTH_SHORT);
        toast.show();
    }*/
}
