package com.example.firstsql;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Comment {

    @Ignore
    public Comment(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Comment(){}

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;
}
