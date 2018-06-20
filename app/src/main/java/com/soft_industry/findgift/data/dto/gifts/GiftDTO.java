package com.soft_industry.findgift.data.dto.gifts;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

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