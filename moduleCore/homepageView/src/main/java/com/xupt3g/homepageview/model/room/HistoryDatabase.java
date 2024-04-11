package com.xupt3g.homepageview.model.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.xupt3g.homepageview.model.room.Dao.HistoryDao;
import com.xupt3g.homepageview.model.room.Data.HistoryData;

/**
 * 项目名: HeartTrip
 * 文件名: HistoryDatabase
 *
 * @author: lukecc0
 * @data:2024/4/10 下午4:12
 * @about: TODO 数据库定义类
 */

@Database(entities = HistoryData.class, version = 1,exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao historyDao();

    private static volatile HistoryDatabase historyDB = null;

    public static HistoryDatabase getInstance(Context context) {
        if (historyDB == null) {
            synchronized (HistoryDatabase.class) {
                if (historyDB == null) {
                    historyDB = Room.databaseBuilder(context.getApplicationContext(), HistoryDatabase.class, "locationHistory")
                            .addCallback(roomCallback)
                            .build();
                }
            }
        }
        return historyDB;
    }

    // 回调函数，可在数据库创建、打开和关闭时执行操作
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }

    };
}
