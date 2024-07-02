package com.alphadle.data.util

import androidx.room.Room
import androidx.room.RoomDatabase
import com.alphadle.data.database.AppDatabase
import com.alphadle.domain.di.AppContext

internal actual fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase> {
    val context = AppContext.context
    val dbFile = context.getDatabasePath("alphadle.db")

    return Room.databaseBuilder<AppDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}
