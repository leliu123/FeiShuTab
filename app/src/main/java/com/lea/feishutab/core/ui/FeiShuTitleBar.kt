package com.lea.feishutab.core.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.lea.feishutab.core.protocol.TitleBarAction

/**
 * 仿飞书风格的TitleBar
 * 支持自定义标题和操作按钮
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeiShuTitleBar(
    title: String,
    actions: List<TitleBarAction> = emptyList(),
    modifier: Modifier = Modifier,
    onSearchClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            // 搜索按钮（可选）
            onSearchClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "搜索"
                    )
                }
            }
            
            // 自定义操作按钮
            actions.forEach { action ->
                IconButton(onClick = action.onClick) {
                    Icon(
                        imageVector = action.icon,
                        contentDescription = action.label
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
    )
}

