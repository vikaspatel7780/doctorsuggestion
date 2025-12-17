package com.myapp.voicehealth.ui.screens.chatScreen

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myapp.voicehealth.data.repository.ChatRepository
import com.myapp.voicehealth.data.roomdb.appDatabase.AppDatabase
import com.myapp.voicehealth.data.roomdb.entity.ChatMessage
import com.myapp.voicehealth.data.roomdb.entity.ChatSession
import kotlinx.coroutines.launch

class ChatViewModel(
    context: Context
) : ViewModel() {

    private val db = AppDatabase.getInstance(context)
    private val repo = ChatRepository(db.chatDao())

    var messages = mutableStateListOf<ChatMessage>()
        private set

    var sessions = mutableStateListOf<ChatSession>()
        private set

    var currentSessionId: Long? = null

    fun startNewChat(title: String) {
        viewModelScope.launch {
            currentSessionId = repo.createChat(title)
            loadMessage(currentSessionId!!)
            loadChats()
        }
    }

    fun sendUserMessage(sessionId: Long, text: String) {
        viewModelScope.launch {
            repo.sendUserMessage(sessionId, text)
            // ✅ Immediately add to UI
            messages.add(0, ChatMessage(
                text = text,
                isUser = true,
                timestamp = System.currentTimeMillis(),
                sessionOwnerId = sessionId
            ))
        }
    }

    fun sendAssistantMessage(sessionId: Long, text: String) {
        viewModelScope.launch {
            repo.sendAssistantMessage(sessionId, text)
            // ✅ Immediately add to UI
            messages.add(0, ChatMessage(
                text = text,
                isUser = false,
                timestamp = System.currentTimeMillis(),
                sessionOwnerId = sessionId
            ))
        }
    }

    fun loadMessage(sessionId: Long) {
        viewModelScope.launch {
            repo.loadMessages(sessionId).collect { dbMessages ->
                messages.clear()
                // Add in reverse order since LazyColumn uses reverseLayout
                dbMessages.forEach { msg ->
                    messages.add(
                        ChatMessage(
                            text = msg.text,
                            isUser = msg.isUser,
                            timestamp = msg.timestamp,
                            sessionOwnerId = msg.sessionOwnerId
                        )
                    )
                }
            }
        }
    }

    fun loadChats() {
        viewModelScope.launch {
            sessions.clear()
            sessions.addAll(repo.loadAllChats())
        }
    }

    fun deleteChat(sessionId: Long) {
        viewModelScope.launch {
            repo.deleteChat(sessionId)
            loadChats()
        }
    }
}

class ChatViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(context.applicationContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}