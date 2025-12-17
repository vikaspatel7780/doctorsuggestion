package com.myapp.voicehealth.ui.screens.chatScreen

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.myapp.voicehealth.data.repository.GeminiService
import com.myapp.voicehealth.data.repository.QuotaExceededException
import com.myapp.voicehealth.data.roomdb.entity.ChatMessage
import com.myapp.voicehealth.ui.components.BottomInputBar
import com.myapp.voicehealth.ui.components.CommonTopBar
import com.myapp.voicehealth.ui.components.DrawerContentChat
import com.myapp.voicehealth.ui.theme.PrimaryBlue
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskAIScreen(navController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showQuotaPopup by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val chatViewModel: ChatViewModel = viewModel(
        factory = ChatViewModelFactory(context)
    )

    val messages = chatViewModel.messages
    var isTyping by remember { mutableStateOf(false) }

    val sessionId = 2L

    LaunchedEffect(sessionId) {
        chatViewModel.loadMessage(sessionId)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContentChat(
                onNewChat = {
                    if (chatViewModel.currentSessionId == null) {
                        chatViewModel.startNewChat("AI Chat")
                    }
                },
                onHistory = {},
                onClose = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                CommonTopBar(
                    title = "AI Suggestion",
                    drawerState = drawerState,
                    showDrawer = true,
                    onBack = { navController.navigate("home_Screen") }
                )
            },
            bottomBar = {
                BottomInputBar(
                    onSend = { text ->
                        if (text.isBlank()) return@BottomInputBar

                        scope.launch {
                            // 1️⃣ Show user message
                            chatViewModel.sendUserMessage(sessionId, text)

                            // 2️⃣ Show typing indicator
                            isTyping = true
                            delay(3000)

                            try {
                                val response = GeminiService.ask(
                                    prompt = text,
                                    apiKey = "AIzaSyBPzbgvtQXLiNniV2xAaMybwFGYKwXtL3w"
                                )

                                // 3️⃣ Hide typing & show assistant message
                                chatViewModel.sendAssistantMessage(sessionId, response)
                                isTyping = false


                            } catch (e: QuotaExceededException) {
                                isTyping = false
                                showQuotaPopup = true

                            } catch (e: Exception) {
                                isTyping = false
                                chatViewModel.sendAssistantMessage(
                                    sessionId,
                                    "Something went wrong. Please try again."
                                )
                            }
                        }
                    }
                )
            }
        ) { padding ->

            if (messages.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = padding.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                            bottom = padding.calculateBottomPadding())
                        .imePadding(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "I’m your health assistant.\nWhat would you like help with today?",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .padding(
                            top = padding.calculateTopPadding(),
                            start = 16.dp,
                            end = 16.dp,
                            bottom = padding.calculateBottomPadding()
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    reverseLayout = true
                ) {

                    // ✅ Typing indicator MUST come FIRST
                    if (isTyping) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                TypingIndicatorBubble()
                            }
                        }
                    }

                    // Messages
                    items(messages.asReversed()) { message ->
                        MessageBubble(message)
                    }
                }

            }
        }

        if (showQuotaPopup) {
            AlertDialog(
                onDismissRequest = { showQuotaPopup = false },
                confirmButton = {
                    TextButton(onClick = { showQuotaPopup = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Daily Limit Reached") },
                text = {
                    Text(
                        "You’ve reached today’s free Gemini API limit.\n\n" +
                                "Please wait 24 hours or upgrade your Google AI plan."
                    )
                }
            )
        }
    }
}
@Composable
fun MessageBubble(message: ChatMessage) {

    val timeText = remember(message.timestamp) {
        SimpleDateFormat("hh:mm a", Locale.getDefault())
            .format(Date(message.timestamp))
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isUser) PrimaryBlue else Color.DarkGray,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Column {
                Text(
                    text = message.text,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = timeText,
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
        }
    }
}



@Composable
fun TypingIndicatorBubble() {
    val dotCount = 3
    val infiniteTransition = rememberInfiniteTransition()
    val animatedDots = (0 until dotCount).map { index ->
        infiniteTransition.animateFloat(
            initialValue = 0.3f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(500, delayMillis = index * 150),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

    Box(
        modifier = Modifier
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            animatedDots.forEach { anim ->
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color.White.copy(alpha = anim.value), shape = RoundedCornerShape(50))
                )
            }
        }
    }
}
