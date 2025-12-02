package com.lea.feishutab.core.tab

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lea.feishulibrary.protocol.TabContext
import com.lea.feishulibrary.protocol.TabTheme
import com.lea.feishulibrary.protocol.TitleBarAction
import com.lea.feishutab.core.ui.FeiShuTitleBar

/**
 * Tab容器主组件 - 仿飞书Tab设计
 * 采用MVI架构，通过ViewModel管理状态
 */
@Composable
fun TabContainer(
    modifier: Modifier = Modifier,
    viewModel: TabViewModel = viewModel(),
    titleBarActions: Map<String, List<TitleBarAction>> = emptyMap()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val isDarkMode = isSystemInDarkTheme()
    
    // 在 @Composable 上下文中获取主题颜色
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    
    // 创建TabContext实现
    val tabContext = remember(isDarkMode, primaryColor, backgroundColor) {
        object : TabContext {
            override fun showToast(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            
            override fun navigateToTab(tabId: String) {
                viewModel.handleIntent(TabIntent.SelectTab(tabId))
            }
            

            override fun getTheme(): TabTheme {
                return TabTheme(
                    isDarkMode = isDarkMode,
                    primaryColor = primaryColor,
                    backgroundColor = backgroundColor
                )
            }
        }
    }
    
    // 处理错误显示
    LaunchedEffect(state.error) {
        state.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.handleIntent(TabIntent.ClearError)
        }
    }
    
    NavigationSuiteScaffold(
        navigationSuiteItems = {
            state.tabs.forEach { tab ->
                item(
                    icon = {
                        Icon(
                            imageVector = tab.tabIcon,
                            contentDescription = tab.tabName
                        )
                    },
                    label = { Text(tab.tabName) },
                    selected = tab.tabId == state.selectedTabId,
                    onClick = {
                        viewModel.handleIntent(TabIntent.SelectTab(tab.tabId))
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = modifier.fillMaxSize(),
            topBar = {
                state.selectedTab?.let { tab ->
                    FeiShuTitleBar(
                        title = tab.tabName,
                        actions = titleBarActions[tab.tabId] ?: emptyList()
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                // 显示加载状态
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    // 显示选中的Tab内容
                    when (val tab = state.selectedTab) {
                        null -> {
                            // 没有选中Tab时显示空状态
                            EmptyTabContent()
                        }
                        else -> {
                            tab.TabContent(context = tabContext)
                        }
                    }
                }
            }
        }
    }
}

/**
 * 空Tab内容占位
 */
@Composable
private fun EmptyTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "暂无Tab",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

