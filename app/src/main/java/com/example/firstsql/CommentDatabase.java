package com.example.firstsql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Comment.class},version = 1)
public abstract class CommentDatabase extends RoomDatabase {
    public abstract CommentDao commentDao();
}
