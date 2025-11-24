package com.lea.feishutab.core.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Tab容器的ViewModel - 采用MVI架构
 * 管理Tab的状态和业务逻辑
 */
class TabViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(TabState())
    val state: StateFlow<TabState> = _state.asStateFlow()
    
    init {
        // 初始化时加载Tab列表
        handleIntent(TabIntent.InitializeTabs)
    }
    
    /**
     * 处理用户意图
     */
    fun handleIntent(intent: TabIntent) {
        viewModelScope.launch {
            when (intent) {
                is TabIntent.InitializeTabs -> {
                    initializeTabs()
                }
                is TabIntent.SelectTab -> {
                    selectTab(intent.tabId)
                }
                is TabIntent.RefreshTabs -> {
                    refreshTabs()
                }
                is TabIntent.ClearError -> {
                    clearError()
                }
            }
        }
    }
    
    /**
     * 初始化Tab列表
     */
    private fun initializeTabs() {
        _state.update { currentState ->
            try {
                val tabs = TabRegistry.getAllTabs()
                currentState.copy(
                    tabs = tabs,
                    selectedTabId = tabs.firstOrNull()?.tabId,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                currentState.copy(
                    isLoading = false,
                    error = e.message ?: "初始化Tab列表失败"
                )
            }
        }
    }
    
    /**
     * 选择Tab
     */
    private fun selectTab(tabId: String) {
        _state.update { currentState ->
            // 通知之前的Tab取消选中
            currentState.selectedTabId?.let { previousTabId ->
                TabRegistry.getTab(previousTabId)?.onTabDeselected()
                TabRegistry.notifyTabPause(previousTabId)
            }
            
            // 通知新的Tab选中
            TabRegistry.getTab(tabId)?.onTabSelected()
            TabRegistry.notifyTabResume(tabId)
            
            currentState.copy(
                selectedTabId = tabId,
                error = null
            )
        }
    }
    
    /**
     * 刷新Tab列表
     */
    private fun refreshTabs() {
        _state.update { currentState ->
            currentState.copy(isLoading = true)
        }
        
        _state.update { currentState ->
            try {
                val tabs = TabRegistry.getAllTabs()
                // 如果当前选中的Tab被移除了，选择第一个Tab
                val newSelectedTabId = if (currentState.selectedTabId != null &&
                    tabs.none { it.tabId == currentState.selectedTabId }) {
                    tabs.firstOrNull()?.tabId
                } else {
                    currentState.selectedTabId
                }
                
                currentState.copy(
                    tabs = tabs,
                    selectedTabId = newSelectedTabId,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                currentState.copy(
                    isLoading = false,
                    error = e.message ?: "刷新Tab列表失败"
                )
            }
        }
    }
    
    /**
     * 清除错误
     */
    private fun clearError() {
        _state.update { currentState ->
            currentState.copy(error = null)
        }
    }
}

