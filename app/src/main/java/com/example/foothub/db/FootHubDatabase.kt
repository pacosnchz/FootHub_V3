package com.example.foothub.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteEntity::class, CommentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FootHubDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile private var INSTANCE: FootHubDatabase? = null

        fun getInstance(context: Context): FootHubDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FootHubDatabase::class.java,
                    "foothub.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}