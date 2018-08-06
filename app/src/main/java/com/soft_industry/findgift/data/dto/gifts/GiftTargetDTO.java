package com.soft_industry.findgift.data.dto.gifts;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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