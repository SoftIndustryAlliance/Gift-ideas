package com.soft_industry.findgift.data.dto.gifts;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


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