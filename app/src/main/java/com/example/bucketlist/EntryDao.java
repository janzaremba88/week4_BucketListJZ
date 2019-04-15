package com.example.bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao

public interface EntryDao {

    @Query("SELECT * FROM entry")
    List<Entry> getAllEntries();

    @Insert
    void insertEntry(Entry entry);

    @Delete
    void deleteEntry(Entry entry);

    @Delete
    void deleteAll(List<Entry> entries);

    @Update
    void updateEntry(Entry entry);
}