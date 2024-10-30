package com.example.cw1_c;

import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvTasks;
    ArrayAdapter<Task> adapter;
    ArrayList<Task> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TaskDao taskDao = new TaskDao(db);

        lvTasks = findViewById(R.id.listViewTasks);
        taskList = taskDao.getAllTasks(MainActivity.this);

        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, taskList);
        lvTasks.setAdapter(adapter);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(v -> showDialog());

        lvTasks.setOnItemClickListener((parent, view, position, id) -> {
            showDialogEdit(position);
        });
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_create, null);

        EditText etTaskName = view.findViewById(R.id.etTaskName);
        EditText etDescription = view.findViewById(R.id.editTextDescription);
        EditText etPhone = view.findViewById(R.id.etPhone);
        TimePicker etStartTime = view.findViewById(R.id.timePickerStartTime);
        TimePicker etEndTime = view.findViewById(R.id.timePickerEndTime);
        Button btnSave = view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String taskName = etTaskName.getText().toString();
            String taskDescription = etDescription.getText().toString();
            String phone = etPhone.getText().toString();


            int startHour = etStartTime.getHour();
            int startMinute = etStartTime.getMinute();
            int endHour = etEndTime.getHour();
            int endMinute = etEndTime.getMinute();


            String startTime = String.format("%02d:%02d", startHour, startMinute);
            String endTime = String.format("%02d:%02d", endHour, endMinute);

            if (taskName.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a task name", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper dbHelper = new dbHelper(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                TaskDao taskDao = new TaskDao(db);

                boolean success = taskDao.addTask(taskName, taskDescription, phone, startTime, endTime);

                if (success) {
                    Toast.makeText(MainActivity.this, "Task added successfully!", Toast.LENGTH_SHORT).show();
                    taskList = taskDao.getAllTasks(MainActivity.this);
                    adapter.clear();
                    adapter.addAll(taskList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to add task.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setView(view)
                .setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());

        Dialog dialog = builder.create();
        dialog.show();
    }


    private void showDialogEdit(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_edit, null);

        EditText etTaskName = view.findViewById(R.id.etTaskName);
        EditText etDescription = view.findViewById(R.id.etDescription);
        EditText etPhone = view.findViewById(R.id.etPhone);
        EditText etStartTime = view.findViewById(R.id.etStartTime);
        EditText etEndTime = view.findViewById(R.id.etEndTime);
        Button btnEdit = view.findViewById(R.id.btnSave);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Task selectedTask = taskList.get(position);
        etTaskName.setText(selectedTask.getName());
        etDescription.setText(selectedTask.getDescription());
        etPhone.setText(selectedTask.getPhone());
        etStartTime.setText(selectedTask.getStartTime());
        etEndTime.setText(selectedTask.getEndTime());

        dbHelper dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        TaskDao taskDao = new TaskDao(db);

        btnEdit.setOnClickListener(v -> {
            String taskName = etTaskName.getText().toString();
            String taskDescription = etDescription.getText().toString();
            String phone = etPhone.getText().toString();
            String startTime = etStartTime.getText().toString();
            String endTime = etEndTime.getText().toString();

            if (taskName.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter task name", Toast.LENGTH_SHORT).show();
            } else {
                boolean isUpdated = taskDao.updateTask(selectedTask.getId(), taskName, taskDescription, phone, startTime, endTime); // Cập nhật phương thức sửa tác vụ
                if (isUpdated) {
                    taskList = taskDao.getAllTasks(MainActivity.this);
                    adapter.clear();
                    adapter.addAll(taskList);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MainActivity.this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to update task", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            boolean isDeleted = taskDao.deleteTask(selectedTask.getId());
            if (isDeleted) {
                taskList = taskDao.getAllTasks(MainActivity.this);
                adapter.clear();
                adapter.addAll(taskList);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Failed to delete task", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setView(view).setNegativeButton("CANCEL", (dialog, which) -> dialog.dismiss());
        Dialog dialog = builder.create();
        dialog.show();
    }
}
