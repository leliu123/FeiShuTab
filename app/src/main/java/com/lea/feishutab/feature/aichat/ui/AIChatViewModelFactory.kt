package com.lea.feishutab.feature.aichat.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lea.feishutab.feature.aichat.data.database.ChatDatabase
import com.lea.feishutab.feature.aichat.data.repository.ChatRepository
class AIChatViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AIChatViewModel::class.java)) {
            val database = ChatDatabase.getDataBase(context)
            val repository = ChatRepository(database = database)
            @Suppress("UNCHECKED_CAST")
            return AIChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

