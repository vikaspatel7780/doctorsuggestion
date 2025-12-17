package com.myapp.voicehealth.data.repository

import com.myapp.voicehealth.data.roomdb.doaFile.ChatDao
import com.myapp.voicehealth.data.roomdb.entity.ChatMessage
import com.myapp.voicehealth.data.roomdb.entity.ChatSession
import kotlinx.coroutines.flow.Flow

class ChatRepository(
    private val dao: ChatDao
) {

    suspend fun createChat(title: String): Long {
        return dao.createChat(ChatSession(title = title))
    }

    suspend fun sendUserMessage(sessionId: Long, text: String) {
        dao.insertMessage(ChatMessage(sessionOwnerId = sessionId, text = text, isUser = true))
    }

    suspend fun sendAssistantMessage(sessionId: Long, text: String) {
        dao.insertMessage(ChatMessage(sessionOwnerId = sessionId, text = text, isUser = false))
    }

    fun loadMessages(sessionId: Long): Flow<List<ChatMessage>> {
        return dao.getMessages(sessionId)
    }

    suspend fun loadAllChats() =
        dao.getAllChats()

    suspend fun deleteChat(sessionId: Long) =
        dao.deleteChat(sessionId)
}
