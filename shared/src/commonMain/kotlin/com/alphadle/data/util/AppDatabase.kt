package com.alphadle.data.util

import androidx.room.RoomDatabase
import com.alphadle.data.database.AppDatabase

internal expect fun getAppDatabaseBuilder(): RoomDatabase.Builder<AppDatabase>
