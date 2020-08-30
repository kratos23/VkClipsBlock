package com.pavelkrylov.vkclipsban

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.concurrent.TimeUnit


class MyAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        stateLd.value = STATE.ACTIVE

        val timeElapsed = System.currentTimeMillis() - SettingsManager.serviceEnableRequestTime
        if (timeElapsed <= RETURN_TO_ACTIVITY_MAX_DELAY) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun onVkClipsClicked() {
        if (SettingsManager.blockEnabled) {
            performGlobalAction(GLOBAL_ACTION_BACK)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
            val node = event.source
            val resName = node?.viewIdResourceName
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
        val RETURN_TO_ACTIVITY_MAX_DELAY = TimeUnit.MINUTES.toMillis(2)

        private val stateLd = MutableLiveData<STATE>()

        init {
            stateLd.value = STATE.IN_ACTIVE
        }

        fun getStateLD(): LiveData<STATE> = stateLd
    }
}