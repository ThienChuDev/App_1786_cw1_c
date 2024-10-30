package com.example.cw1_c;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 3;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TASKS_TABLE = "CREATE TABLE tasks ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "description TEXT, "
                + "phone TEXT, "
                + "start_time TEXT, "
                + "end_time TEXT)";
        db.execSQL(CREATE_TASKS_TABLE);

        String INSERT_TASK_1 = "INSERT INTO tasks (name, description, phone, start_time, end_time) VALUES ('Task 1', 'Description for Task 1', '123456789', '08:00', '09:00')";
        String INSERT_TASK_2 = "INSERT INTO tasks (name, description, phone, start_time, end_time) VALUES ('Task 2', 'Description for Task 2', '987654321', '10:00', '11:00')";
        String INSERT_TASK_3 = "INSERT INTO tasks (name, description, phone, start_time, end_time) VALUES ('Task 3', 'Description for Task 3', '555666777', '12:00', '13:00')";

        db.execSQL(INSERT_TASK_1);
        db.execSQL(INSERT_TASK_2);
        db.execSQL(INSERT_TASK_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
}
