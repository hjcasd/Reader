package com.hjc.learn.main.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class History(
    var name: String,
    var time: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}