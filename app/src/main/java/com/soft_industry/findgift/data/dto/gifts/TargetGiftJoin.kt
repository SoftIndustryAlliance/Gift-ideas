package com.soft_industry.findgift.data.dto.gifts

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Created by user on 4/23/18.
 */
@Entity(tableName = "targets_gift",
primaryKeys = arrayOf("gift_id", "target_id"),
indices = arrayOf(Index("target_id")),
foreignKeys = arrayOf(ForeignKey(
        entity = GiftDTO::class,
                childColumns = arrayOf("gift_id"),
        onDelete = 5,
        parentColumns = arrayOf("id")
), ForeignKey(
    entity = GiftTargetDTO::class,
    childColumns = arrayOf("target_id"),
    onDelete = 5,
    parentColumns = arrayOf("id")
    ))
)
data class TargetGiftJoin(var gift_id: Long = 0, var target_id: Long = 0)