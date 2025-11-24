package com.lea.feishutab.feature.message

/**
 * 消息Tab的UI状态
 */
data class MessageState(
    /**
     * 消息列表
     */
    val messages: List<MessageItem> = emptyList(),
    
    /**
     * 是否正在加载
     */
    val isLoading: Boolean = false,
    
    /**
     * 是否正在刷新
     */
    val isRefreshing: Boolean = false,
    
    /**
     * 错误信息
     */
    val error: String? = null,
    
    /**
     * 未读消息数量
     */
    val unreadCount: Int = 0
) {
    /**
     * 是否有错误
     */
    val hasError: Boolean
        get() = error != null
}

/**
 * 消息项数据模型
 */
data class MessageItem(
    val id: String,
    val title: String,
    val content: String,
    val sender: String,
    val timestamp: Long,
    val isRead: Boolean = false,
    val avatar: String? = null
)

