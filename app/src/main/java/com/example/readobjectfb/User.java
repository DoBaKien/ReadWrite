package com.example.readobjectfb;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int id;
    private String name;
    private Job job;
    private String address;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public User(int id, String name, Job job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", job=" + job +
                ", address='" + address + '\'' +
                '}';
    }

    public User(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("name", name);
        return result;
    }
}

