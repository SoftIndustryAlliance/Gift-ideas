package com.soft_industry.findgift.data.dto.gifts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "gifts")
public final class GiftDTO {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "name")
    @NotNull
    public String name;
    @ColumnInfo(name = "icon")
    @NotNull
    public String icon;
    @ColumnInfo(name = "caption")
    @NotNull
    public String caption;
}