package com.soft_industry.findgift.data.dto.gifts;

import org.jetbrains.annotations.NotNull;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(
        tableName = "shop_type"
)
public final class ShopTypeDTO {
    @PrimaryKey(autoGenerate = true)
    public long id;
    @ColumnInfo(name = "label")
    @NotNull
    public String label;

}