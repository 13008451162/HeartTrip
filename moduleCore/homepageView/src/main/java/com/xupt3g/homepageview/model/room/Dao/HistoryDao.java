package com.xupt3g.homepageview.model.room.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xupt3g.homepageview.model.room.Data.HistoryData;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

/**
 * 项目名: HeartTrip
 * 文件名: HistoryDao
 *
 * @author: lukecc0
 * @data:2024/4/10 下午3:51
 * @about: TODO 历史搜索的数据访问对象
 */

@Dao
public interface HistoryDao {

    /**
     * 向数据库添加数据
     *
     * @param data
     */
    @Insert
    void insertData(HistoryData data);


    /**
     * 删除数据库所有数据
     */
    @Query("DELETE FROM HistoryData")
    void deleteDataAll();

    /**
     * 获取所有历史数据
     *
     * @return 所有历史数据列表
     */
    @Query("SELECT * FROM HistoryData")
    Observable<List<HistoryData>> getAllData();
}
