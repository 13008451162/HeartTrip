package com.xupt3g.homepageview.model.room.Data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目名: HeartTrip
 * 文件名: HistoryData
 *
 * @author: lukecc0
 * @data:2024/4/9 下午5:25
 * @about: TODO 搜索的历史记录
 */

@Entity()
public class HistoryData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "Name")
    public String locationName;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    @Override
    public String toString() {
        return "HistoryData{" +
                "id=" + id +
                ", locationName='" + locationName + '\'' +
                '}';
    }
}
