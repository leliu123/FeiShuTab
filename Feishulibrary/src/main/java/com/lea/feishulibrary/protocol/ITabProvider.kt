package com.lea.feishulibrary.protocol

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Tab提供者接口 - 外部开发者需要实现此接口来接入Tab
 * 
 * 这是框架对外的主要接口，设计时考虑了：
 * 1. 稳定性：接口方法签名保持向后兼容
 * 2. 灵活性：支持自定义图标、标题、内容等
 * 3. 生命周期：提供完整的生命周期回调
 */
interface ITabProvider {
    /**
     * Tab的唯一标识符
     * 建议格式：com.yourcompany.tabname
     */
    val tabId: String
    
    /**
     * Tab显示名称
     */
    val tabName: String
    
    /**
     * Tab图标
     */
    val tabIcon: ImageVector
    
    /**
     * Tab优先级，数字越小优先级越高，越靠前显示
     * 默认值为100
     */
    val priority: Int
        get() = 100
    
    /**
     * 创建Tab内容Composable
     * @param context Tab上下文，提供框架能力
     */
    @Composable
    fun TabContent(context: TabContext): Unit
    
    /**
     * Tab生命周期回调 - 当Tab被选中时调用
     */
    fun onTabSelected() {}
    
    /**
     * Tab生命周期回调 - 当Tab被取消选中时调用
     */
    fun onTabDeselected() {}
    
    /**
     * Tab生命周期回调 - 当Tab被销毁时调用
     */
    fun onTabDestroyed() {}
}

