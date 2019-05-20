package com.gourav.livedataproject;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    public Note(String title, String desc, int priority) {
        this.title = title;
        this.priority = priority;
        this.desc = desc;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }



    public int getPriority() {
        return priority;
    }


    public String getDesc() {
        return desc;
    }


    private int priority;
    private String desc;



}
