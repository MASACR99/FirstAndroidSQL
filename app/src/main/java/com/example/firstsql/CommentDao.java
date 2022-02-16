package com.example.firstsql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CommentDao {
    @Query("SELECT * FROM comment")
    List<Comment> getAll();

    @Query("SELECT * FROM comment WHERE uid = :uid")
    List<Comment> getById(int uid);

    @Query("SELECT * FROM comment WHERE title LIKE :title")
    List<Comment> getByTitle(String title);

    @Insert
    void insertAll(Comment... comments);

    @Insert
    void insertOne(Comment comment);

    @Delete
    void delete(Comment comment);
}
