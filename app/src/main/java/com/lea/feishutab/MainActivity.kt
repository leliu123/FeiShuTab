package com.lea.feishutab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.lea.feishutab.ui.theme.FeiShuTabTheme
import com.lea.feishutab.core.tab.TabContainer
import com.lea.feishutab.core.tab.TabRegistry
import com.lea.feishutab.feature.message.MessageTabProvider
import com.lea.feishutab.feature.aichat.AIChatTabProvider
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // 注册所有Tab
            // 这里可以注册多个Tab，框架会自动管理
            TabRegistry.registerTab(MessageTabProvider())
            TabRegistry.registerTab(AIChatTabProvider())
            // 可以继续注册其他Tab...
            // TabRegistry.registerTab(OtherTabProvider())

            FeiShuTabApp()
            
        }
    }
}

@Preview
@Composable
fun FeiShuTabApp() {
    // 使用Tab容器组件，采用MVI架构管理状态
    TabContainer()
}