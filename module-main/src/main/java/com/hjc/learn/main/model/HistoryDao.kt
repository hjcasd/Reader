package com.hjc.learn.main.model

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


    @Query("DELETE FROM history")
    fun deleteAll()

    @Query("SELECT * FROM history")
    fun getAllHistory(): List<History>?

}