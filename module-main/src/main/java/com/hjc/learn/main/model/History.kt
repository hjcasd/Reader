package com.hjc.learn.main.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class History {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var name = ""

    override fun toString(): String {
        return "History(id=$id, name='$name')"
    }

}