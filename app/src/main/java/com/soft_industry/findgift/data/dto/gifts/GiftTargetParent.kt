package com.soft_industry.findgift.data.dto.gifts

/**
 * Created by user on 4/18/18.
 */


enum class GiftTargetParent(val id: Int) {
    Editors(0),
    Thematic(1),
    ForWomen(2),
    ForMen(3)
}


//@Entity(tableName = "gift_targets")
//data class GiftTargetDTO(@PrimaryKey(autoGenerate = true) var id: Long =0,
//                         @ColumnInfo(name = "label") var label: String = "",
//                         @ColumnInfo(name="icon") var icon: String = "",
//                         @ColumnInfo(name = "parent") var parent: Int = 0
//)

//@Entity(tableName = "gifts")
//data class GiftDTO(@PrimaryKey(autoGenerate = true) var id: Long = 0,
//                   @ColumnInfo(name = "target_id") var targetId: Long = 0,
//                   @ColumnInfo(name = "name") var name: String ="",
//                   @ColumnInfo(name = "icon") var icon: String = "",
//                   @ColumnInfo(name = "caption") var caption: String =""
//)

//@Entity(tableName = "shop_type")
//data class ShopTypeDTO(@PrimaryKey(autoGenerate = true) var id: Long =0,
//                       @ColumnInfo(name = "label") var label: String = "")
//@Entity(tableName = "gift_shop_types",
//        primaryKeys = arrayOf("gift_id", "repoId"),
//        foreignKeys = arrayOf(
//                ForeignKey(entity = GiftDTO::class,
//                        parentColumns = arrayOf("id"),
//                        childColumns = arrayOf("gift_id"),
//                        onDelete = ForeignKey.CASCADE),
//                ForeignKey(entity = ShopTypeDTO::class,
//                        parentColumns = arrayOf("id"),
//                        childColumns = arrayOf("shop_type_id"),
//                        onDelete = ForeignKey.CASCADE))
//)
//data class GiftShopsDTO(@PrimaryKey(autoGenerate = true) var id: Long = 0,
//                        @ColumnInfo(name = "gift_id") var giftId: Long = 0,
//                        @ColumnInfo(name = "shop_type_id") var shopTypeId: Long = 0
//)