package com.soft_industry.findgift.data.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "db_population")
class DBPopulator(@PrimaryKey(autoGenerate = true) var id: Int? = null,var timestamp: Long? = null)