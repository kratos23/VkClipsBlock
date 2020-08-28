package com.pavelkrylov.vkclipsban

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        stateLd.value = STATE.ACTIVE
    }

    private fun onVkClipsClicked() {
        performGlobalAction(GLOBAL_ACTION_BACK)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val node = event.source
            val resName = node.viewIdResourceName
            if (CLIPS_VIEW_ID == resName) {
                onVkClipsClicked()
            }
        }
    }

    override fun onInterrupt() {
    }

    override fun onDestroy() {
        super.onDestroy()
        stateLd.value = STATE.IN_ACTIVE
    }

    enum class STATE {
        ACTIVE,
        IN_ACTIVE
    }

    companion object {
        const val CLIPS_VIEW_ID = "com.vkontakte.android:id/tab_feedback"

        private val stateLd = MutableLiveData<STATE>()

        init {
            stateLd.value = STATE.IN_ACTIVE
        }

        fun getStateLD(): LiveData<STATE> = stateLd
    }
}