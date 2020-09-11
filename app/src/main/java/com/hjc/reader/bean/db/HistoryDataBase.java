package com.hjc.reader.bean.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {History.class}, version = 1, exportSchema = false)
public abstract class HistoryDataBase extends RoomDatabase {

    private static final String DB_NAME = "search_history";

    private static HistoryDataBase historyDataBase;

    public static HistoryDataBase getInstance(Context context) {
        if (historyDataBase == null) {
            synchronized (HistoryDataBase.class) {
                historyDataBase = Room.databaseBuilder(
                        context.getApplicationContext(),
                        HistoryDataBase.class,
                        DB_NAME
                ).allowMainThreadQueries().build();
            }
        }
        return historyDataBase;
    }

    public abstract HistoryDao getHistoryDao();
}
