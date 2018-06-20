package com.soft_industry.findgift.data.datasources.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.jakewharton.rxrelay2.BehaviorRelay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.concurrent.Executors

class DbCallback (private val context: Context, val relay: BehaviorRelay<Boolean>) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Executors.newSingleThreadExecutor().execute {
            prepopulateData(db)
        }
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
    }

    private fun prepopulateData(db: SupportSQLiteDatabase) {

        val stream = InputStreamReader(context.getAssets()
                .open("initial_data.sql"))
        val reader = BufferedReader(stream)
        var line: String? = reader.readLine()
        while (line != null) {
            line.takeIf { !it.isBlank() }
                    ?.let { sql ->
//                        Log.e("TEST", sql)
                        db.execSQL(sql)
                    }
            line = reader.readLine()
        }
        relay.accept(true)
    }
}