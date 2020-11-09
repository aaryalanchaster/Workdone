package com.application.task;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class OnClickTask implements View.OnClickListener {
    Context context;
    String id;
    int id1;

    @Override
    public void onClick(View view) {
        context = view.getContext();
        id = view.getTag().toString();
        id1 = view.getId();
        boolean checkUpdate = updated(id1);
        if(checkUpdate){
        CheckBox checked = (CheckBox) view.findViewById(id1);
        checked.setPaintFlags(checked.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
        Log.i("id1", String.valueOf(id1));
        Log.i("id", id);
       }

    }

    public boolean updated(int id) {
        return new TableHandler(context).checked(id);

    }
}
