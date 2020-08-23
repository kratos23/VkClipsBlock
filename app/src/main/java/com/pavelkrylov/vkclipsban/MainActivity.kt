package com.pavelkrylov.vkclipsban

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.main.*


class MainActivity : AppCompatActivity(R.layout.main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToSettingsBtn.setOnClickListener {
            val goToSettings = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            goToSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(goToSettings)
        }
    }
}