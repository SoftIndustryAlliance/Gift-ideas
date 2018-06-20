package com.soft_industry.findgift.data.dto.gifts;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(
        tableName = "gift_targets"
)
public final class GiftTargetDTO {
    @PrimaryKey(
            autoGenerate = true
    )
    public long id;
    @ColumnInfo(
            name = "label"
    )
    @NotNull
    public String label;
    @ColumnInfo(
            name = "icon"
    )
    @NotNull
    public String icon;
    @ColumnInfo(
            name = "parent"
    )
    public int parent;
}