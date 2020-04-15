package com.example.android_mvvm.room_repository;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "myNotes")
public class Note {

    @NonNull
    @PrimaryKey
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String desc;

    @NonNull
    private int priority;


    public Note(@NonNull String id, @NonNull String title, @NonNull String desc, int priority) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.priority = priority;
    }


    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getDesc() {
        return desc;
    }

    public int getPriority() {
        return priority;
    }


}
