package com.alfredoalmenares.memorandum;

// POJO for database objects
public class CustomTask {
    int id;
    String desc;
    String title; // Short version of desc

    public CustomTask(int id, String desc) {
        this.id = id;
        this.desc = desc;
        this.title = this.desc.substring(0, Math.min(20, desc.length()));
    }

    public String getTitle() {
        return title;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

}
