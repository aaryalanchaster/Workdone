package com.application.task;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getSessions()) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        } else {
            setContentView(R.layout.activity_main);
//            countRecords();
            readTask();
            FloatingActionButton fab = findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Context context = view.getRootView().getContext();
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addTask = inflater.inflate(R.layout.dialog, null, false);
                    final EditText task = addTask.findViewById(R.id.taskadd);
                    new AlertDialog.Builder(context)
                            .setView(addTask)
                            .setTitle("Add Task")
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String taskstring = task.getText().toString();
                                    Task t = new Task();
                                    t.task = taskstring;
                                    boolean added = new TableHandler(context).add(t,getemail());
                                    if (added) {
                                        Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show();
//                                        countRecords();
                                        readTask();
                                    }
                                }
                            }).show();
                }
            });
        }

    }

    public boolean getSessions() {
        SetSession session;
        session = new SetSession(getApplicationContext());
        if (session.getemail().isEmpty() && session.getpass().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
    public String getemail(){
        SetSession session = new SetSession(getApplicationContext());
        String email = session.getemail();
        return email;
    }

    public int get_uid(){
        SetSession session = new SetSession(getApplicationContext());
        String email = session.getemail();
        int id = new TableHandler(this).get_id(email);
        return id;
    }
//    public void countRecords () {
//            int records = new TableHandler(this).count();
//            TextView textRecords = (TextView) findViewById(R.id.textViewRecordCount);
//            textRecords.setText(records + "task found");
//        }

        public void readTask () {
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayoutRecords);
            int uid = get_uid();
            linearLayout.removeAllViews();
            List<Task> lst = new TableHandler(this).read(uid);
            if (lst.size() > 0) {
                for (Task t : lst) {
                    String task = t.task;
                    int id = t.id;
                    Boolean checked = t.completed;
                    CardView.LayoutParams layoutParams = new CardView.LayoutParams(
                            CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.MATCH_PARENT
                    );
                    layoutParams.setMargins(15, 0, 15, 30);
                    CardView cardView = new CardView(this);
                    cardView.setLayoutParams(layoutParams);
                    cardView.setElevation(30);
                    CheckBox checkBox = new CheckBox(this);
                    checkBox.setPadding(0, 20, 0, 20);
                    checkBox.setTextSize(20);
                    checkBox.setText(task);
                    checkBox.setChecked(checked);
                    checkBox.setTag(Integer.toString(id));
                    checkBox.setId(id);
                    checkBox.setOnClickListener(new OnClickTask());
                    checkBox.setOnLongClickListener(new OnLongClickTask());
                    cardView.addView(checkBox);
                    linearLayout.addView(cardView);
                }
            } else {
                TextView locationItem = new TextView(this);
                locationItem.setPadding(8, 8, 8, 8);
                locationItem.setText("Add task to get started");
                locationItem.setGravity(Gravity.CENTER);
                locationItem.setTextSize(20);
                linearLayout.addView(locationItem);
            }
        }

        public void logOut(){
            SetSession session;
            session = new SetSession(getApplicationContext());
            Log.i("BLogout", session.getemail());
            Log.i("BLogout1", session.getpass());
            session.logout();
            Log.i("Logout", session.getemail());
            Log.i("Logout1", session.getpass());

            }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logOut();
            Toast.makeText(getApplicationContext(), "Logout", Toast.LENGTH_LONG).show();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            return true;

        } else if (id == R.id.action_settings) {
            Toast.makeText(getApplicationContext(), "Work in Progress", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }


}