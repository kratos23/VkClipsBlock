package com.pavelkrylov.vkclipsban

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.enable_service_fragment.*

class AccessibilityEnableFragment : Fragment(R.layout.enable_service_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        enableBnt.setOnClickListener {
            val goToSettings = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            goToSettings.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_NO_HISTORY
                        or Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
            startActivity(goToSettings)
            SettingsManager.serviceEnableRequestTime = System.currentTimeMillis()
            Toast.makeText(context, R.string.enable_service_hint, Toast.LENGTH_SHORT).show()
        }
    }
}