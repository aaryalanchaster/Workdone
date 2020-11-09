package com.application.task;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText email = findViewById(R.id.lemail);
        final EditText pass = findViewById(R.id.lPassword);

        findViewById(R.id.loginbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uemail = email.getText().toString();
                String upass = pass.getText().toString();
                Boolean val = null;
                if (uemail.isEmpty() || upass.isEmpty()){
                    Toast.makeText(Login.this,"ENTER ALL DETAILS!!",Toast.LENGTH_LONG).show();
                }
                else {
                         val = check_login(uemail,upass);
                    if (val) {
                       setSession(uemail, upass);
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(getApplicationContext(),Register.class);
                        startActivity(i);
                        Toast.makeText(Login.this, "Please Register", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

//        findViewById(R.id.Signupbtn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(),Register.class);
//                startActivity(i);
//            }
//        });

        findViewById(R.id.signinText).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),Register.class);
                startActivity(i);
            }
        });
    }

    public boolean check_login(String uemail,String upass){
        Boolean aBoolean = null;
        try{
        aBoolean = new TableHandler(this).check_user(uemail, upass);}
        catch (SQLiteException e) {
            Toast.makeText(Login.this, "ERROR WHILE CHECKING", Toast.LENGTH_LONG).show();
        }
        return aBoolean;
    }

    public void setSession(String email,String password){
        SetSession session;
        session=new SetSession(getApplicationContext());
        session.setemail(email);
        session.setpass(password);
        Log.i("Session", session.getemail());
        Log.i("Session1", session.getpass());
    }


}