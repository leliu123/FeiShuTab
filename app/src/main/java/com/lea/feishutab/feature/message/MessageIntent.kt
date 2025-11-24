package com.lea.feishutab.feature.message

/**
 * 消息Tab的用户意图/事件
 */
sealed class MessageIntent {
    /**
     * 加载消息列表
     */
    object LoadMessages : MessageIntent()
    
    /**
     * 刷新消息列表
     */
    object RefreshMessages : MessageIntent()
    
    /**
     * 标记消息为已读
     */
    data class MarkAsRead(val messageId: String) : MessageIntent()
    
    /**
     * 删除消息
     */
    data class DeleteMessage(val messageId: String) : MessageIntent()
    
    /**
     * 清除错误
     */
    object ClearError : MessageIntent()
}

