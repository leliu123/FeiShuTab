package com.lea.feishutab.feature.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * 消息Tab的ViewModel - 采用MVI架构
 */
class MessageViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(MessageState())
    val state: StateFlow<MessageState> = _state.asStateFlow()
    
    init {
        // 初始化时加载消息
        handleIntent(MessageIntent.LoadMessages)
    }
    
    /**
     * 处理用户意图
     */
    fun handleIntent(intent: MessageIntent) {
        viewModelScope.launch {
            when (intent) {
                is MessageIntent.LoadMessages -> {
                    loadMessages()
                }
                is MessageIntent.RefreshMessages -> {
                    refreshMessages()
                }
                is MessageIntent.MarkAsRead -> {
                    markAsRead(intent.messageId)
                }
                is MessageIntent.DeleteMessage -> {
                    deleteMessage(intent.messageId)
                }
                is MessageIntent.ClearError -> {
                    clearError()
                }
            }
        }
    }
    
    /**
     * 加载消息列表
     */
    private suspend fun loadMessages() {
        _state.update { it.copy(isLoading = true, error = null) }
        
        try {
            // 模拟网络请求延迟
            delay(1000)
            
            // 模拟消息数据
            val messages = generateMockMessages()
            
            _state.update {
                it.copy(
                    messages = messages,
                    isLoading = false,
                    unreadCount = messages.count { !it.isRead }
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isLoading = false,
                    error = e.message ?: "加载消息失败"
                )
            }
        }
    }
    
    /**
     * 刷新消息列表
     */
    private suspend fun refreshMessages() {
        _state.update { it.copy(isRefreshing = true, error = null) }
        
        try {
            // 模拟网络请求延迟
            delay(800)
            
            // 模拟消息数据
            val messages = generateMockMessages()
            
            _state.update {
                it.copy(
                    messages = messages,
                    isRefreshing = false,
                    unreadCount = messages.count { !it.isRead }
                )
            }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isRefreshing = false,
                    error = e.message ?: "刷新消息失败"
                )
            }
        }
    }
    
    /**
     * 标记消息为已读
     */
    private fun markAsRead(messageId: String) {
        _state.update { currentState ->
            val updatedMessages = currentState.messages.map { message ->
                if (message.id == messageId && !message.isRead) {
                    message.copy(isRead = true)
                } else {
                    message
                }
            }
            
            currentState.copy(
                messages = updatedMessages,
                unreadCount = updatedMessages.count { !it.isRead }
            )
        }
    }
    
    /**
     * 删除消息
     */
    private fun deleteMessage(messageId: String) {
        _state.update { currentState ->
            val updatedMessages = currentState.messages.filter { it.id != messageId }
            
            currentState.copy(
                messages = updatedMessages,
                unreadCount = updatedMessages.count { !it.isRead }
            )
        }
    }
    
    /**
     * 清除错误
     */
    private fun clearError() {
        _state.update { it.copy(error = null) }
    }
    
    /**
     * 生成模拟消息数据
     */
    private fun generateMockMessages(): List<MessageItem> {
        return listOf(
            MessageItem(
                id = "1",
                title = "系统通知",
                content = "您有一条新的系统消息，请及时查看",
                sender = "系统",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 5, // 5分钟前
                isRead = false
            ),
            MessageItem(
                id = "2",
                title = "团队协作",
                content = "张三邀请您加入项目讨论",
                sender = "张三",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 30, // 30分钟前
                isRead = false
            ),
            MessageItem(
                id = "3",
                title = "会议提醒",
                content = "下午3点有团队会议，请准时参加",
                sender = "李四",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 2, // 2小时前
                isRead = true
            ),
            MessageItem(
                id = "4",
                title = "任务更新",
                content = "您的任务状态已更新为已完成",
                sender = "系统",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 5, // 5小时前
                isRead = true
            ),
            MessageItem(
                id = "5",
                title = "文档分享",
                content = "王五分享了文档《项目计划书》",
                sender = "王五",
                timestamp = System.currentTimeMillis() - 1000 * 60 * 60 * 24, // 1天前
                isRead = true
            )
        )
    }
}

