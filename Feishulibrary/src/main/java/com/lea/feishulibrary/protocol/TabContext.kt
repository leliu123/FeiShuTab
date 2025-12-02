package com.lea.feishulibrary.protocol

import androidx.compose.ui.graphics.Color

/**
 * Tab上下文 - 提供给外部开发者的框架能力
 */
interface TabContext {
    /**
     * 显示Toast消息
     */
    fun showToast(message: String)
    
    /**
     * 导航到其他Tab
     */
    fun navigateToTab(tabId: String)
    
    /**
     * 获取当前主题配置
     */
    fun getTheme(): TabTheme
}

/**
 * Tab主题配置
 */

data class TabTheme(
    val isDarkMode: Boolean,
    val primaryColor: Color,
    val backgroundColor: Color
)

