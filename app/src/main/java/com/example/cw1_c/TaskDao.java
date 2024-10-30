package com.example.cw1_c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class TaskDao {
    private SQLiteDatabase db;

    public TaskDao(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean addTask(String taskName, String taskDescription, String phone, String startTime, String endTime) {
        ContentValues values = new ContentValues();
        values.put("name", taskName);
        values.put("description", taskDescription);
        values.put("phone", phone);
        values.put("start_time", startTime);
        values.put("end_time", endTime);
        long result = db.insert("tasks", null, values);
        return result != -1;
    }

    public boolean updateTask(int taskId, String taskName, String taskDescription, String phone, String startTime, String endTime) {
        ContentValues values = new ContentValues();
        values.put("name", taskName);
        values.put("description", taskDescription);
        values.put("phone", phone);
        values.put("start_time", startTime);
        values.put("end_time", endTime);
        int rowsAffected = db.update("tasks", values, "id = ?", new String[]{String.valueOf(taskId)});
        return rowsAffected > 0;
    }

    public boolean deleteTask(int taskId) {
        int rowsAffected = db.delete("tasks", "id = ?", new String[]{String.valueOf(taskId)});
        return rowsAffected > 0;
    }

    public ArrayList<Task> getAllTasks(Context context) {
        ArrayList<Task> tasksList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int taskId = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String phone = cursor.getString(3);
                String startTime = cursor.getString(4);
                String endTime = cursor.getString(5);

                Task task = new Task(taskId, name, description, phone, startTime, endTime);
                tasksList.add(task);
            } while (cursor.moveToNext());
            cursor.close();
        }
        Log.d("tasks Info", tasksList.toString());
        return tasksList;
    }
}
