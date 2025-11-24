package com.lea.feishutab.core.tab

import com.lea.feishutab.core.protocol.ITabProvider
import com.lea.feishutab.core.protocol.TabLifecycle

/**
 * Tab注册中心 - 管理所有注册的Tab
 * 采用单例模式，确保全局唯一
 */
object TabRegistry {
    private val tabs = mutableMapOf<String, ITabProvider>()
    private val lifecycleListeners = mutableMapOf<String, TabLifecycle>()
    
    /**
     * 注册Tab
     * @param provider Tab提供者
     * @return 是否注册成功
     */
    fun registerTab(provider: ITabProvider): Boolean {
        return if (tabs.containsKey(provider.tabId)) {
            false // Tab已存在
        } else {
            tabs[provider.tabId] = provider
            if (provider is TabLifecycle) {
                lifecycleListeners[provider.tabId] = provider
                provider.onCreate()
            }
            true
        }
    }
    
    /**
     * 注销Tab
     */
    fun unregisterTab(tabId: String) {
        tabs.remove(tabId)?.let { provider ->
            if (provider is TabLifecycle) {
                lifecycleListeners.remove(tabId)?.onDestroy()
            }
            provider.onTabDestroyed()
        }
    }
    
    /**
     * 获取所有已注册的Tab，按优先级排序
     */
    fun getAllTabs(): List<ITabProvider> {
        return tabs.values.sortedBy { it.priority }
    }
    
    /**
     * 根据ID获取Tab
     */
    fun getTab(tabId: String): ITabProvider? {
        return tabs[tabId]
    }
    
    /**
     * 通知Tab生命周期事件
     */
    fun notifyTabResume(tabId: String) {
        lifecycleListeners[tabId]?.onResume()
    }
    
    fun notifyTabPause(tabId: String) {
        lifecycleListeners[tabId]?.onPause()
    }
}

