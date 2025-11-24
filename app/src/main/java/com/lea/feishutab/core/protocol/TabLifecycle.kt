package com.lea.feishutab.core.protocol

/**
 * Tab生命周期管理接口
 * 外部开发者可以实现此接口来响应生命周期事件
 */
interface TabLifecycle {
    /**
     * Tab创建时调用（首次注册时）
     */
    fun onCreate() {}
    
    /**
     * Tab可见时调用（切换到该Tab时）
     */
    fun onResume() {}
    
    /**
     * Tab不可见时调用（切换到其他Tab时）
     */
    fun onPause() {}
    
    /**
     * Tab销毁时调用（从注册中心移除时）
     */
    fun onDestroy() {}
}

