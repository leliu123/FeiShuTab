package com.lea.feishutab.feature.aichat.ui

import com.lea.feishutab.feature.aichat.data.chat.ChatMessage

data class ChatUiState(
    val chatMessages: List<ChatMessage> = emptyList(),
    val inputText: String="",
    val isSending: Boolean = false,
    val isLoading: Boolean = false,
    val modelName: String = "",
    val error: String? = null

)

