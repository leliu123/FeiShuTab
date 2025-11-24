package com.lea.feishutab.feature.message

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import com.lea.feishutab.core.protocol.ITabProvider
import com.lea.feishutab.core.protocol.TabContext
import com.lea.feishutab.core.protocol.TabLifecycle

/**
 * 消息Tab提供者
 * 实现ITabProvider接口，展示如何使用MVI架构接入Tab
 */
class MessageTabProvider : ITabProvider, TabLifecycle {
    override val tabId: String = "com.lea.feishutab.message"
    override val tabName: String = "消息"
    override val tabIcon = Icons.Filled.Call
    override val priority: Int = 1 // 最高优先级，显示在最前面
    
    @Composable
    override fun TabContent(context: TabContext) {
        MessageScreen(context = context)
    }
    
    override fun onCreate() {
        // Tab创建时的初始化逻辑
        // 例如：初始化消息服务、注册推送监听等
    }
    
    override fun onResume() {
        // Tab可见时的逻辑
        // 例如：刷新消息列表、恢复消息推送等
    }
    
    override fun onPause() {
        // Tab不可见时的逻辑
        // 例如：暂停消息推送、保存状态等
    }
    
    override fun onDestroy() {
        // Tab销毁时的清理逻辑
        // 例如：取消网络请求、释放资源等
    }
}

