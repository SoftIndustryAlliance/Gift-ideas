package com.soft_industry.findgift.data.dto.gifts;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;


@Entity(
        tableName = "gift_shop_types",
        primaryKeys = {"gift_id", "shop_type_id"},
        indices = {@Index("shop_type_id")},
        foreignKeys = {@ForeignKey(
                entity = GiftDTO.class,
                childColumns = {"gift_id"},
                onDelete = 5,
                parentColumns = {"id"}
        ), @ForeignKey(
                entity = ShopTypeDTO.class,
                childColumns = {"shop_type_id"},
                onDelete = 5,
                parentColumns = {"id"}
        )}
)
public final class GiftShopsDTO {

    @ColumnInfo(
            name = "gift_id"
    )
    public long giftId;
    @ColumnInfo(
            name = "shop_type_id"
    )
    public long shopTypeId;

}