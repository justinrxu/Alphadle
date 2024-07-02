package com.alphadle.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alphadle.data.dao.WordStatsDao
import com.alphadle.data.entity.WordStats
import com.alphadle.data.util.Converters

@Database(entities = [WordStats::class], version = 1)
@TypeConverters(Converters::class)
internal abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun getWordStatsDao(): WordStatsDao

    override fun clearAllTables() {
        super.clearAllTables()
    }
}

// FIXME: Added a hack to resolve below issue:
// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}