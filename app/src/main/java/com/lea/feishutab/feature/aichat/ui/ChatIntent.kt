package com.lea.feishutab.feature.aichat.ui

sealed class ChatIntent {
    data class InputTextChange(val text: String): ChatIntent()


    data object SendMessage: ChatIntent()
    data object ClearChat: ChatIntent()




}

