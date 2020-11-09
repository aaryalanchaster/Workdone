package com.application.task;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class SetSession {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SetSession(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);

    }
    public void setemail(String email) {
        prefs.edit().putString("email", email).apply();
    }

    public String getemail() {
        return prefs.getString("email","");
    }
    public void setpass(String pass) {
        prefs.edit().putString("password", pass).apply();
    }

    public String getpass() {
        return prefs.getString("password","");
    }

    public void logout(){
         editor = prefs.edit();
         editor.clear();
         editor.apply();
    }
}