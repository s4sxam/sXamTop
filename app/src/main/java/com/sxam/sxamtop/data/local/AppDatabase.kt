package com.sxam.sxamtop.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =[BookmarkEntity::class, UserPostEntity::class, NewsCacheEntity::class],
    version = 3, // Bumped to 3 for BookmarkEntity schema change
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun userPostDao(): UserPostDao
    abstract fun newsCacheDao(): NewsCacheDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "sxamtop_db")
                    .fallbackToDestructiveMigration() // Retained for now per instructions
                    .build().also { INSTANCE = it }
            }
        }
    }
}