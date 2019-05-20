package com.gourav.livedataproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note node);

    @Delete
    void delete(Note node);

    @Update
    void update(Note node);

    @Query("Delete from note_table")
    void deleteAllNotes();

    @Query("Select * from note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();


}
