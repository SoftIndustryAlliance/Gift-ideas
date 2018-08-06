package com.soft_industry.findgift.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "db_population")
class DBPopulator(@PrimaryKey(autoGenerate = true) var id: Int? = null,var timestamp: Long? = null)