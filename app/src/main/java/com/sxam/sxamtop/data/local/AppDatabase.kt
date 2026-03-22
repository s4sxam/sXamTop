package com.sxam.sxamtop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookmarkEntity::class, UserPostEntity::class, NewsCacheEntity::class], // Added NewsCacheEntity
    version = 2, // #28 Bumped version
    exportSchema = true // #27 Fixed
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun userPostDao(): UserPostDao
    abstract fun newsCacheDao(): NewsCacheDao // #9 Caching DAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "sxamtop_db")
                    .fallbackToDestructiveMigration() // Wipe DB to apply v2 schema safely
                    .build().also { INSTANCE = it }
            }
        }
    }
}