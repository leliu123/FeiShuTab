package com.lea.feishutab.core.tab

/**
 * Tab容器的用户意图/事件
 * 采用密封类设计，便于扩展和类型安全
 */
sealed class TabIntent {
    /**
     * 初始化Tab列表
     */
    object InitializeTabs : TabIntent()
    
    /**
     * 选择Tab
     */
    data class SelectTab(val tabId: String) : TabIntent()
    
    /**
     * 刷新Tab列表
     */
    object RefreshTabs : TabIntent()
    
    /**
     * 清除错误
     */
    object ClearError : TabIntent()
}

