package com.example.cw1_c;

public class Task {
    private int id;
    private String name;
    private String description;
    private String phone;
    private String startTime;
    private String endTime;

    public Task(int id, String name, String description, String phone, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Task \n" +
                "name: " + name + "\n" +
                "description: " + description + "\n" +
                "phone: " + phone + "\n" +
                "start time: " + startTime + "\n" +
                "end time: " + endTime + "\n";
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
