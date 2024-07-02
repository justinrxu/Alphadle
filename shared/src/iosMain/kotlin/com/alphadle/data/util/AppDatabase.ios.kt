package com.alphadle.data.util

import androidx.room.Room
import androidx.room.RoomDatabase
import com.alphadle.data.database.AppDatabase
import com.alphadle.data.database.instantiateImpl
import platform.Foundation.NSHomeDirectory

internal actual fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val dbFile = NSHomeDirectory() + "/alphadle.db"

    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class.instantiateImpl() }
    )
}
