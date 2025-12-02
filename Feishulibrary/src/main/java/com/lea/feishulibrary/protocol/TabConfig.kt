package com.lea.feishulibrary.protocol

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Tab配置信息 - 用于Tab的元数据描述
 */
data class TabConfig(
    val tabId: String,
    val tabName: String,
    val tabIcon: ImageVector,
    val priority: Int = 100,
    val enableCustomTitleBar: Boolean = false,
    val titleBarActions: List<TitleBarAction> = emptyList()
)

/**
 * TitleBar自定义操作按钮
 */
data class TitleBarAction(
    val icon: ImageVector,
    val label: String,
    val onClick: () -> Unit
)

