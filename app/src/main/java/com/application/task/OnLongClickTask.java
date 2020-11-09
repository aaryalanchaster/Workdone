package com.application.task;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class OnLongClickTask implements View.OnLongClickListener {
    Context context;
    String id;
    int id1;

    @Override
    public boolean onLongClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        id1 = view.getId();
        new AlertDialog.Builder(context).setTitle("Are you sure you want to delete the task?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean deleteSuccessful = new TableHandler(context).delete(Integer.parseInt(id));
                        if (deleteSuccessful) {
                            Toast.makeText(context, "Task was deleted.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Unable to delete task.", Toast.LENGTH_SHORT).show();
                        }
                        ((MainActivity) context).readTask();
                    }
                }).show();
        return false;
    }

}
