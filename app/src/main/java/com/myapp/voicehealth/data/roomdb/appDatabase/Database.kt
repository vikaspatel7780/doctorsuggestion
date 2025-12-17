package com.myapp.voicehealth.data.roomdb.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapp.voicehealth.data.roomdb.doaFile.ChatDao
import com.myapp.voicehealth.data.roomdb.entity.ChatMessage
import com.myapp.voicehealth.data.roomdb.entity.ChatSession

@Database(
    entities = [ChatSession::class, ChatMessage::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "voicehealth_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

