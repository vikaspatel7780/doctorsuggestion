package com.myapp.voicehealth.data.roomdb.doaFile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.voicehealth.data.roomdb.entity.ChatMessage
import com.myapp.voicehealth.data.roomdb.entity.ChatSession
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Insert
    suspend fun createChat(chat: ChatSession): Long

    @Insert
    suspend fun insertMessage(message: ChatMessage)

    @Query("SELECT * FROM chat_sessions ORDER BY createdAt DESC")
    suspend fun getAllChats(): List<ChatSession>

    @Query("SELECT * FROM chat_messages WHERE sessionOwnerId = :sessionId ORDER BY timestamp ASC")
    fun getMessages(sessionId: Long): Flow<List<ChatMessage>>


    @Query("DELETE FROM chat_sessions WHERE sessionId = :sessionId")
    suspend fun deleteChat(sessionId: Long)

    @Query("DELETE FROM chat_sessions")
    suspend fun deleteAllChats()
}

