package com.application.task;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TableHandler extends DatabaseHandler {
    public TableHandler(@Nullable Context context) {
        super(context);
    }

    public void insert_user(String name,String email,String password){
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("EMAIL",email);
        contentValues.put("PASSWORD",password);
        this.getWritableDatabase().insertOrThrow("User","",contentValues);
    }

    public boolean check_user(String email, String password){
         SQLiteDatabase db=getWritableDatabase();
        Cursor cursor = db.query("User",null,"EMAIL=?",new String[]{email},null,null,null);
        if (cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.moveToFirst();
        String pass = cursor.getString(cursor.getColumnIndex("PASSWORD"));
        return password.equals(pass);
    }

    public int get_id(String email){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor = db.query("User",null,"EMAIL=?",new String[]{email},null,null,null);
        if (cursor.getCount()<=0){
            cursor.close();
            return -1;
        }
        cursor.moveToFirst();
        return Integer.parseInt(cursor.getString(cursor.getColumnIndex("ID")));
    }

    public boolean add(Task task,String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("u_id", get_id(email));
        contentValues.put("completed", task.completed);
        contentValues.put("task", task.task);
        SQLiteDatabase db = this.getWritableDatabase();
        boolean added = db.insert("task", null, contentValues) > 0;
        db.close();
        return added;
    }

    public int count() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM task";
        int records = db.rawQuery(sql, null).getCount();
        db.close();
        return records;
    }

    public List<Task> read(int id) {
        List<Task> lst = new ArrayList<Task>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("task", null,"u_id=?",new String[]{String.valueOf(id)},null,null,null);
        if (cursor.moveToFirst()) {
            do {
                String task = cursor.getString(cursor.getColumnIndex("task"));
                String id_t = cursor.getString(cursor.getColumnIndex("id"));
                String completed = cursor.getString(cursor.getColumnIndex("completed"));
                Task t = new Task();
                t.task = task;
                t.id = Integer.parseInt(id_t);
                t.completed=Boolean.parseBoolean(completed);

                lst.add(t);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lst;
    }

    public boolean checked(int id){
        ContentValues values = new ContentValues();
        Task t = get_single_task(id);
        boolean complete;
        complete= !t.completed;
        values.put("completed", complete);
        String where = "id=?";
        String[ ]whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        boolean update = db.update("task", values, where, whereArgs)>0;
        db.close();
        return update;
    }

    public Task get_single_task(int id){
        Task t = null;
        String sql="SELECT * FROM task where id = "+id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            boolean completed = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("completed")));
            String task = cursor.getString(cursor.getColumnIndex("task"));
            t= new Task();
            t.completed = completed;
            t.task=task;
        }
        cursor.close();
        db.close();
        return t;
    }

    public boolean delete(int id) {
        boolean deleteSuccessful = false;
        SQLiteDatabase db = this.getWritableDatabase();
        deleteSuccessful = db.delete("task", "id ='" + id + "'", null) > 0;
        db.close();
        return deleteSuccessful;

    }
}
