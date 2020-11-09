package com.application.task;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById(R.id.rsingup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((EditText) findViewById(R.id.remail)).getText().toString();
                final String pass = ((EditText) findViewById(R.id.rpassword)).getText().toString();
                final String name = ((EditText) findViewById(R.id.rname)).getText().toString();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                if (name.equals("") || email.equals("") || pass.equals("")) {
                    Snackbar.make(view, "Please enter all the data", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {
                    insertUser(name, email, pass);
                    setSession(email, pass);
                    Intent i = new Intent(Register.this, MainActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    public void insertUser(String name, String email, String password) {
        try {
            new TableHandler(this).insert_user(name, email, password);
        } catch (SQLiteException e) {
            Toast.makeText(Register.this, "ALREADY EXIST", Toast.LENGTH_LONG).show();
        }
    }

    public void setSession(String email, String password) {
        SetSession session;
        session = new SetSession(getApplicationContext());
        session.setemail(email);
        session.setpass(password);
        Log.i("Session", session.getemail());
        Log.i("Session1", session.getpass());
    }

}