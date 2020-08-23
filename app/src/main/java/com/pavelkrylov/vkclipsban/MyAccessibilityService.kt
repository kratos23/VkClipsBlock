package com.pavelkrylov.vkclipsban

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MyAccessibilityService : AccessibilityService() {
    override fun onCreate() {
        super.onCreate()
        println("CREATED")
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        println("Connected")
    }

    private fun dfs(node: AccessibilityNodeInfo) {
        println("DFS = " + node.viewIdResourceName)
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            dfs(child)
        }
    }

    private fun onVkClipsClicked() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val node = event.source
            val resName = node.viewIdResourceName
            println("VK click $resName")
            if (CLIPS_VIEW_ID == resName) {
                onVkClipsClicked()
            }
        }
        //println(event)
    }

    override fun onInterrupt() {

    }

    companion object {
        const val CLIPS_VIEW_ID = "com.vkontakte.android:id/tab_feedback"
        const val NEWS_VIEW_ID = "com.vkontakte.android:id/tab_news"
    }
}