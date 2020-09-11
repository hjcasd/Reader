package com.hjc.reader.bean.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Insert
    void insert(History history);

    @Delete
    void delete(History history);


    @Query("DELETE FROM history")
    void deleteAll();

    @Query("SELECT * FROM history")
    List<History> getAllHistory();

}
