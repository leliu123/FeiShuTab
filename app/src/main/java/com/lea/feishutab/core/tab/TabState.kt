package com.lea.feishutab.core.tab

import com.lea.feishutab.core.protocol.ITabProvider

/**
 * Tab容器的UI状态
 */
data class TabState(
    /**
     * 所有已注册的Tab列表
     */
    val tabs: List<ITabProvider> = emptyList(),
    
    /**
     * 当前选中的Tab ID
     */
    val selectedTabId: String? = null,
    
    /**
     * 是否正在加载
     */
    val isLoading: Boolean = false,
    
    /**
     * 错误信息
     */
    val error: String? = null
) {
    /**
     * 获取当前选中的Tab
     */
    val selectedTab: ITabProvider?
        get() = selectedTabId?.let { tabId ->
            tabs.firstOrNull { it.tabId == tabId }
        }
}

