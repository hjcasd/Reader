package com.hjc.learn.main.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [History::class], version = 1)
abstract class HistoryDataBase : RoomDatabase() {

    // 必须包含一个具有0个参数且返回带@Dao注释的类的抽象方法
    abstract fun getHistoryDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: HistoryDataBase? = null

        fun getInstance(context: Context): HistoryDataBase = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                HistoryDataBase::class.java, "search_history.db"
            )
                .allowMainThreadQueries()
                .build()
    }
}