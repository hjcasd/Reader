package com.hjc.learn.main.entity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Insert
    fun insert(history: History)

    @Delete
    fun delete(history: History)

    @Query("DELETE FROM history_table")
    fun deleteAll()

    @Query("SELECT * FROM history_table ORDER BY time DESC")
    fun getAllHistory(): List<History>?

}