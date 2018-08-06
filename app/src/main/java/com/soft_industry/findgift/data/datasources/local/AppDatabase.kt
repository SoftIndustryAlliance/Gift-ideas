package com.soft_industry.findgift.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soft_industry.findgift.data.dto.DBPopulator
import com.soft_industry.findgift.data.dto.gifts.*


/**
 * Created by user on 4/18/18.
 */
@Database(entities = arrayOf(GiftDTO::class,GiftTargetDTO::class, ShopTypeDTO::class, GiftShopsDTO::class, TargetGiftJoin::class, DBPopulator::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun dataDao(): DataDao
    abstract fun populationDao(): PopulationDao
}